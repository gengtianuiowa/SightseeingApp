package cn.edu.whu.tiangeng.controller;


import cn.edu.whu.tiangeng.domain.siteInfo;
import cn.edu.whu.tiangeng.services.getnearsiteImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;

@Controller
public class getnearsiteController {

    @ResponseBody
    @RequestMapping("getnearsite")
    public String getnearsite(double lat, double lng) {
        getnearsiteImpl nearsite = new getnearsiteImpl();
        List<siteInfo> infos = nearsite.getnearsite();
        int num = infos.size();
        siteInfo info = new siteInfo();


        JSONObject jsonObject = new JSONObject();
        JSONObject tjo = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < num; i++) {
            info = infos.get(i);
            info.getName();
            double lng1 = info.getPoint_x();
            double lat1 = info.getPoint_y();
            double radLat1 = Math.toRadians(lat1);
            double radLat = Math.toRadians(lat);
            double a = radLat1 - radLat;
            double b = Math.toRadians(lng1) - Math.toRadians(lng);
            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                    * Math.cos(radLat) * Math.pow(Math.sin(b / 2), 2)));
            s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
            s = Math.round(s * 10000) / 10000;
            tjo.put("order", i);
            tjo.put("name", info.getName());
            tjo.put("distance", s);
            jsonArray.put(i,tjo.toString());
        }

       jsonArray=paixu(jsonArray);

        JSONArray jsonArray1=new JSONArray();
        for(int i=0;i<10;i++){
            String str=(String) jsonArray.get(i);
            jsonArray1.put(i,str);
        }


        //  tjo.put("order", info.getOrder());
        jsonObject.put("nearsitelist", jsonArray1);
        return jsonObject.toString();

    }

    private JSONArray paixu(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            Double min = 100000.0;
            int count=0;

            for (int j = i; j < jsonArray.length(); j++) {
                String str=(String) jsonArray.get(j);
                JSONObject jsonObject=new JSONObject(str);
                double distance = jsonObject.getDouble("distance");
                if (distance < min) {
                    min = distance;
                    count=j;
    //               jsonArray1.put(i, jsonObject.toString());
                }
            }
            String js=(String)jsonArray.get(i);
            jsonArray.put(i,jsonArray.get(count));
            jsonArray.put(count,js);

        }
        return jsonArray;
    }
}