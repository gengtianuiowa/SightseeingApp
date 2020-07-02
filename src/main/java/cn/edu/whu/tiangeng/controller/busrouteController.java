package cn.edu.whu.tiangeng.controller;

import cn.edu.whu.tiangeng.domain.busrouteBean;
import cn.edu.whu.tiangeng.services.busrouteService;
import cn.edu.whu.tiangeng.services.busrouteServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class busrouteController {
    @ResponseBody
    @RequestMapping("busroute")
    public String busroutequery(double startlat,double startlng,String endname){

        ResultSet r= null;
        try {
            //创建对象
            busrouteBean b=new busrouteBean();
            busrouteServiceImpl bus=new busrouteServiceImpl();

            //获取出发站坐标
            double[] startstation=new double[2];
            startstation[0]=bus.findstation(startlng,startlng)[0];
            startstation[1]=bus.findstation(startlng,startlng)[1];

            //获取出发站的geojson
            JSONObject stationgeojson1=bus.stationgeojson(startstation[0],startstation[1]);

            //获取发点到出发站的geojson和时间
            JSONObject routegeojson1=bus.searchroute_w(startlat,startlng,startstation[0],startstation[1],0);
            double[] routetime=new double[3];
            routetime[0]=busrouteServiceImpl.TIME[0];

            //获取目的地坐标，并存在d[]里
            double[] d=new double[2];
            d[0]=bus.des_cor(endname)[0];
            d[1]=bus.des_cor(endname)[1];

            //获取结束站坐标
            double[] endstation=new double[2];
            endstation[0]=bus.findstation(d[0],d[1])[0];
            endstation[1]=bus.findstation(d[0],d[1])[1];

            //获取结束站的geojson
            JSONObject stationgeojson2=bus.stationgeojson(endstation[0],endstation[1]);

            //获取终点到结束站的geojson和时间
            JSONObject routegeojson3=bus.searchroute_w(endstation[0],endstation[1],d[0],d[1],1);
            routetime[1]=busrouteServiceImpl.TIME[1];

            //获取经过的站数
            int stationnum=bus.stationnum(startstation,endstation);

            //获取车路线的geojson和时间
            JSONObject routegeojson2=bus.busroutegeojson(startstation,endstation);
            routetime[2]=stationnum*2;

            //获取中途车站的名字
            JSONArray stationnamelist=bus.stationnamelist(startstation,endstation);


            boolean bool;
            if(routegeojson1.equals("")){
                bool=false;
            }else{
                bool=true;
            }
            if(b.status(endname)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", b.status(endname));
                jsonObject.put("isroute", bool);
                jsonObject.put("routetime1",routetime[0]);
                jsonObject.put("routetime2",routetime[1]);
                jsonObject.put("routetime3",routetime[2]);
                jsonObject.put("routegeojson1",routegeojson1);
                jsonObject.put("routegeojson2",routegeojson2);
                jsonObject.put("routegeojson3",routegeojson3);
                jsonObject.put("stationgeojson1",stationgeojson1);
                jsonObject.put("stationgeojson2",stationgeojson2);
                jsonObject.put("stationnamelist",stationnamelist);
                jsonObject.put("stationnum",stationnum);
                return jsonObject.toString();
            }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", b.status(endname));
                jsonObject.put("isroute",false);
                jsonObject.put("routetime1","");
                jsonObject.put("routetime2","");
                jsonObject.put("routetime3","");
                jsonObject.put("routegeojson1","");
                jsonObject.put("routegeojson2","");
                jsonObject.put("routegeojson3","");
                jsonObject.put("stationgeojson1","");
                jsonObject.put("stationgeojson2","");
                jsonObject.put("stationnamelist","");
                jsonObject.put("stationnum","");
                return jsonObject.toString();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
