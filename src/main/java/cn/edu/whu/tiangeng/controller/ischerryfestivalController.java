package cn.edu.whu.tiangeng.controller;


import cn.edu.whu.tiangeng.domain.cherrytimeInfo;
import cn.edu.whu.tiangeng.services.cherrytimeImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ischerryfestivalController {
    @ResponseBody
    @RequestMapping("ischerryfestival")
    public String ischerryfestival(String timestamp) throws ParseException {
        // Date date1=new Date();
        //  timestamp=date1.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date a=simpleDateFormat.parse(timestamp);
//        long lt = new Long(timestamp);
//        Date date = new Date(lt);
//        String T = simpleDateFormat.format(date);

        //ate date=new Date();
        //imestamp=date.getTime();
        cherrytimeImpl cherry = new cherrytimeImpl();
        List<cherrytimeInfo> infos = cherry.getcherrytime();
        int num = infos.size();
        cherrytimeInfo info = new cherrytimeInfo();  // 15827354784

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < num; i++) {
            info = infos.get(i);
            Date starttime = info.getStarttime();
            Date endtime = info.getEndtime();
            //  SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            //   Date date=format.parse(endtime);

            if (a.before(endtime)&&a.after(starttime)) {
                jsonObject.put("status", true);
            } else {
                    jsonObject.put("status", false);
                }
            }
        return jsonObject.toString();
        }

}

