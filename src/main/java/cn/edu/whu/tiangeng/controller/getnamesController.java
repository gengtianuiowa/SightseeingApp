package cn.edu.whu.tiangeng.controller;



import cn.edu.whu.tiangeng.domain.namesInfo;
import cn.edu.whu.tiangeng.domain.siteInfo;
import cn.edu.whu.tiangeng.services.getnamesImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


    @Controller
    public class getnamesController {
        @ResponseBody
        @RequestMapping("getnames")
        public String getnames() {
            getnamesImpl getnames=new getnamesImpl();
            List<namesInfo> infos=getnames.listname();
            namesInfo info=new namesInfo();
            int num= infos.size();

            JSONObject jsonObject = new JSONObject();
            JSONObject tjo = new JSONObject();
            JSONArray jsonArray=new JSONArray();

            for(int i=0;i<num;i++)
            {
                info=infos.get(i);
                info.getName();
                tjo.put("name",info.getName());
                jsonArray.put(i, tjo.toString());
            }


         //   jsonArray.put(0,"资源与环境科学学院");
         //   jsonArray.put(1,"计算机科学学学院");
         //   jsonArray.put(2,"国际软件学院");
          //  jsonArray.put(3,"测绘科学与技术学院");
          //  jsonArray.put(4,"遥感科学与技术学院");
          //  jsonArray.put(5,"药学院");
            jsonObject.put("num",num);
            jsonObject.put("namelist",jsonArray);
            return jsonObject.toString();
        }
    }

