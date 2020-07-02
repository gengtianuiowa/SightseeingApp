package cn.edu.whu.tiangeng.controller;


import cn.edu.whu.test.service.TestService;
import cn.edu.whu.tiangeng.domain.updateInfo;
import cn.edu.whu.tiangeng.services.getupdateImpl;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.JSONObject;


import java.util.List;

/**
 * Created by Sun on 2017/7/26.
 */
@Controller
public class getupdateController {
    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping("getupdate")
    public String getupdate(int versionid) {
        getupdateImpl update = new getupdateImpl();
        List<updateInfo> infos = update.getupdateInfo();
        updateInfo info = new updateInfo();
        int num = infos.size();

        JSONObject jsonObject = new JSONObject();
        JSONObject tjo = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < num; i++) {
            info=infos.get(i);
            info.getVersionname();
            info.getVersionid();
            info.getDownloadurl();
            info.getIsupdate();

            tjo.put("isupdate", info.getIsupdate());
            tjo.put("versionname", info.getVersionname());
            tjo.put("versionid", info.getVersionid());
            tjo.put("downloadurl", info.getDownloadurl());
            jsonArray.put(i, tjo.toString());
//        jsonObject.put("isupdate",true);
//        jsonObject.put("versionname","cartovision_v1.0.1");
//        jsonObject.put("versionid",2);
//        jsonObject.put("downloadurl","http://www.baidu.com");

        }
        jsonObject.put("updatelist",jsonArray);
        return jsonObject.toString();
    }
}