package cn.edu.whu.test.controller;

import cn.edu.whu.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Fly on 2017/7/26.
 */
@Controller
public class TestController {
    @Autowired
    private TestService testService;
    @ResponseBody
    @RequestMapping("test")
    public String test(String lat, String lng) {
        return "经纬度坐标是"+lat+ ","+lng;
        //return testService.getBook(id);
    }
}
