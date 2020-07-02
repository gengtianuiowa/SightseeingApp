package cn.edu.whu.tiangeng.services;





import cn.edu.whu.tiangeng.domain.siteInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class getnearsiteImpl implements getnearsite {
    @Override
    public List<siteInfo> getnearsite() {
        List<siteInfo> infos = new ArrayList<>();
        String name1 = "getnearsite";
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.0.105:5432/xzq", "postgres", "xzq1997");
            Statement statement = connection.createStatement();
            String sqlStr = "SELECT name,point_x,point_y from annotation20170226";
            ResultSet resultset = statement.executeQuery(sqlStr);
            while (resultset.next()) {
                String name = resultset.getString("name");
                String point_x=resultset.getString("point_x");
                String point_y=resultset.getString("point_y");
                //给info对象赋值
                //----------- info.setId
                siteInfo info = new siteInfo();
                info.setName(name);
                info.setPoint_x(point_x);
                info.setPoint_y(point_y);
                infos.add(info);
            }

        }
        //  infos.add(info)

        catch (SQLException var6) {
            System.out.println(var6);

        }
        System.out.println(name1);

        return infos;
    }


//    public double getdistance(double lng1,double lat1,double lng2,double lat2) {
//        double radLat1 = Math.toRadians(lat1);
//        double radLat2 = Math.toRadians(lat2);
//        double a = radLat1 - radLat2;
//        double b = Math.toRadians(lng1) - Math.toRadians(lng2);
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
//                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
//        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
//        s = Math.round(s * 10000) / 10000;
//        return s;
//
//    }
}


