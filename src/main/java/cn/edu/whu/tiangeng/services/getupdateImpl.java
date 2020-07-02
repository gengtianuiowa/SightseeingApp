package cn.edu.whu.tiangeng.services;


import cn.edu.whu.tiangeng.domain.updateInfo;

import java.sql.*;
import java.util.*;

public class getupdateImpl implements getupdate {
    @Override
    public List<updateInfo> getupdateInfo(){
        List<updateInfo> infos=new ArrayList<>();
        String name1="update";
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.0.105:5432/xzq", "postgres", "xzq1997");
            Statement statement = connection.createStatement();
            String sqlStr = "SELECT * from update";
            ResultSet resultset = statement.executeQuery(sqlStr);
            while(resultset.next())
            {
                String isupdate = resultset.getString("isupdate");
                String versionname= resultset.getString("versionname");
                int versionID = resultset.getInt("versionid");
                String downloadurl = resultset.getString("downloadurl");
                /*
                给info对象赋值
                ----------- info.setId
                */
                updateInfo info=new updateInfo();
                info.setIsupdate(isupdate);
                info.setVersionname(versionname);
                info.setVersionid(versionID);
                info.setDownloadurl(downloadurl);
                infos.add(info);
            }


        }
        //  infos.add(info)

        catch(SQLException var6){
            System.out.println(var6);

        }
        System.out.println(name1);

        return infos;
    }

}