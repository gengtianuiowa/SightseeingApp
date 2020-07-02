package cn.edu.whu.tiangeng.controller;

import cn.edu.whu.tiangeng.domain.fuzzyqueryBean;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class fuzzyqueryController {
    @ResponseBody
    @RequestMapping("fuzzyquery")

    public String fuzzyquery(String name){

        ResultSet r= null;
        try { ;
            fuzzyqueryBean b=new fuzzyqueryBean();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("namelist", b.namelist(name));
            jsonObject.put("num", b.num(name));
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
