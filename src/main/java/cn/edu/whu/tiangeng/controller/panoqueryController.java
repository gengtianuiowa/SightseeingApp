package cn.edu.whu.tiangeng.controller;

import cn.edu.whu.tiangeng.domain.panoqueryBean;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class panoqueryController {
    @ResponseBody
    @RequestMapping("panoquery")

    public String panoquery(String name){

        ResultSet r= null;
        try { ;
            panoqueryBean b=new panoqueryBean();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("panourls_land", b.panourls_land(name));
            jsonObject.put("panonum", b.panonum(name));
            jsonObject.put("panourls_air",b.panourls_air(name));
            jsonObject.put("name",name);
            jsonObject.put("ispano",b.ispano(name));
            jsonObject.put("panourls_indoor",b.panourls_indoor(name));
            jsonObject.put("panourl",b.panourl(name));
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
