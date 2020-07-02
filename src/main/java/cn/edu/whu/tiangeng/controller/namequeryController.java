package cn.edu.whu.tiangeng.controller;

import cn.edu.whu.tiangeng.domain.namequeryBean;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class namequeryController {
    @ResponseBody
    @RequestMapping("namequery")

    public String namequery(String name){

        ResultSet r= null;
        try { ;
            namequeryBean b=new namequeryBean();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lng", b.lng(name));
            jsonObject.put("isimg", b.isimg(name));
            jsonObject.put("name",name);
            jsonObject.put("ispano",b.ispano(name));
            jsonObject.put("isaud",b.isaud(name));
            jsonObject.put("type",b.type(name));
            jsonObject.put("lat",b.lat(name));
            jsonObject.put("status",b.status(name));
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
