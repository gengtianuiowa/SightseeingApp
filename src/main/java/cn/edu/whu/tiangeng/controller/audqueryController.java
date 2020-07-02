package cn.edu.whu.tiangeng.controller;

import cn.edu.whu.tiangeng.domain.audqueryBean;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class audqueryController {
    @ResponseBody
    @RequestMapping(value="/audquery")

    public String audquery(String name){

        ResultSet r= null;
        try { ;
            audqueryBean b=new audqueryBean();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("audnum", b.audnum(name));
            jsonObject.put("audurl_f", b.audurl_f(name));
            jsonObject.put("name",name);
            jsonObject.put("audurl_m",b.audurl_m(name));
            jsonObject.put("audurl",b.audurl(name));
            jsonObject.put("isaud",b.isaud(name));
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
