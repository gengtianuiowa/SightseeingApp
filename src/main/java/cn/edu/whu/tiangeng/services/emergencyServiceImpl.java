package cn.edu.whu.tiangeng.services;


import cn.edu.whu.tiangeng.domain.emergencyInfo;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class emergencyServiceImpl implements emergencyService {
    @Override
    public List<emergencyInfo> listemergency() {
        List<emergencyInfo> infos = new ArrayList<emergencyInfo>();
        //创建emergengcyInfo对象，用于临时存数据

        String name1 = "emergency";
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.0.105:5432/xzq", "postgres", "xzq1997");
            Statement statement = connection.createStatement();
            String sqlStr = "SELECT * from emergency";
            ResultSet resultset = statement.executeQuery(sqlStr);
//            resultset.last();
//            int num = resultset.getRow();
//            resultset.beforeFirst();

            // ResultSetMetaData rsmd = resultSet.getMetaData();
            // int count = rsmd.getColumnCount();
            // while (resultset.next()) {
           // resultset.next();
            //   while (resultset.next()) {
//            for (int i = 0; i < num; i++) {
//                String id = resultset.getString("id");
//                String name = resultset.getString("name");
//                String reason = resultset.getString("reason");
//                String phone = resultset.getString("phone");
//                //给info对象赋值
//                //----------- info.setId
//                info.setId(id);
//                info.setName(name);
//                info.setReason(reason);
//                info.setPhone(phone);
//                infos.add(info);
//                resultset.next();
//            }
            while(resultset.next())
            {
                String id = resultset.getString("id");
                String name = resultset.getString("name");
                String reason = resultset.getString("reason");
                String phone = resultset.getString("phone");
                //给info对象赋值
                //----------- info.setId
                emergencyInfo info=new emergencyInfo();
                info.setId(id);
                info.setName(name);
                info.setReason(reason);
                info.setPhone(phone);
                infos.add(info);
               // resultset.next();
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



