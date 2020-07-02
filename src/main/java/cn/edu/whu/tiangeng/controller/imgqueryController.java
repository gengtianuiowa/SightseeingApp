package cn.edu.whu.tiangeng.controller;

import cn.edu.whu.tiangeng.domain.imgqueryBean;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class imgqueryController {
    @ResponseBody
    @RequestMapping("imgquery")

    public String imgquery(String name){

        ResultSet r= null;
        try {
//            r = imgqueryServiceImpl.search(name);
            imgqueryBean b=new imgqueryBean();
            String[] strArray = b.imgurls(name);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isimg", b.isimg(name));
            jsonObject.put("name", name);
            jsonObject.put("imgnum",b.imgnum(name));
            jsonObject.put("imgurl",b.imgurl(name));
            jsonObject.put("imgurls", strArray);
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
     return "false";
    }
}
