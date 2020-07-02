package cn.edu.whu.tiangeng.controller;


import cn.edu.whu.tiangeng.domain.emergencyInfo;
import cn.edu.whu.tiangeng.services.emergencyServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class getemergencyController {

        @ResponseBody
        @RequestMapping("getemergency")
        public String getemergency () {
            //emergencyInfo emer=new emergencyInfo();
            emergencyServiceImpl emer=new  emergencyServiceImpl();
            List<emergencyInfo> infos = emer.listemergency();
            int num= infos.size();
            emergencyInfo info=new emergencyInfo();

            JSONObject jsonObject = new JSONObject();
            JSONObject tjo = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            for(int i=0;i<num;i++)
            {
                info=infos.get(i);
                info.getId();
                info.getName();
                info.getReason();
                info.getPhone();
                tjo.put("id",info.getId());
                tjo.put("name",info.getName());
                tjo.put("reason",info.getReason());
                tjo.put("phone",info.getPhone());
                jsonArray.put(i, tjo.toString());
            }
            jsonObject.put("emergegncylist", jsonArray);
            jsonObject.put("num", infos.size());
            return jsonObject.toString();


           // return "";
//                List<emergencyInfo> infos = new ArrayList<emergencyInfo>();
//                for (int i = 0; i < 4; i++) {
//                    String emer = infos.get(0).getId();
//                    System.out.println(emer);
//                }
//            JSONObject jsonObject = new JSONObject();
//            JSONObject tjo = new JSONObject();
//            JSONArray jsonArray = new JSONArray();
//            tjo.put(id);
//            tjo.put("name", "重大报警");
//            tjo.put("reason", "出现危及生命的情况时，请拨打此电话");
//            tjo.put("phone", "110");
//            jsonArray.put(0, tjo.toString());
//            tjo.put("id", 2);
//            tjo.put("name", "广播台");
//            tjo.put("reason", "和亲人走失请拨打此号码");
//            tjo.put("phone", "152684954321");
//            jsonArray.put(1, tjo.put("id", 3);tjo.toString());
//
//            tjo.put("name", "校医院");
//            tjo.put("reason", "出现紧急受伤情况，请拨打此电话");
//            tjo.put("phone", "10084");
//            jsonArray.put(2, tjo.toString());
//
//
//            jsonObject.put("emergegncylist", jsonArray);
//            jsonObject.put("num", 3);
//            return jsonObject.toString();
//        }

    }
}
