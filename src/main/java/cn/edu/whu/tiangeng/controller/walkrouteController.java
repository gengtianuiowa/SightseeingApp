package cn.edu.whu.tiangeng.controller;

import cn.edu.whu.tiangeng.domain.walkrouteBean;
import cn.edu.whu.tiangeng.services.walkrouteService;
import cn.edu.whu.tiangeng.services.walkrouteServiceImpl;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class walkrouteController {
    @ResponseBody
    @RequestMapping("walkroute")

    public String walkroutequery(double startlat,double startlng,String endname){

        ResultSet r= null;
        try { ;
            walkrouteBean b=new walkrouteBean();
            walkrouteService w=new walkrouteServiceImpl();
            JSONObject geojson=w.searchroute(startlat,startlng,endname);
            boolean bool;
            if(geojson==null){
                bool=false;
            }else{
                bool=true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", b.status(endname));
            jsonObject.put("isroute", bool);
            jsonObject.put("routetime",walkrouteServiceImpl.TIME);
            jsonObject.put("routegeojson",geojson);
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
