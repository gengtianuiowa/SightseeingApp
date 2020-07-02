package cn.edu.whu.tiangeng.services;


import cn.edu.whu.tiangeng.domain.namesInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class getnamesImpl implements getnames {
    public List<namesInfo> listname(){
       List<namesInfo> infos=new ArrayList<>();

        String name1 = "names";
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.0.105:5432/xzq", "postgres", "xzq1997");
            Statement statement = connection.createStatement();
            String sqlStr = "SELECT * from names";
            ResultSet resultset = statement.executeQuery(sqlStr);
            // ResultSetMetaData rsmd = resultSet.getMetaData();
            // int count = rsmd.getColumnCount();
            // while (resultset.next()) {

            //   while (resultset.next()) {
            while(resultset.next()){
                String name = resultset.getString("name");
                //给info对象赋值
                //----------- info.setId
                namesInfo info= new namesInfo();
                info.setName(name);
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

