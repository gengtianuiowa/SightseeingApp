package cn.edu.whu.tiangeng.services;



import cn.edu.whu.tiangeng.domain.cherrytimeInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class cherrytimeImpl implements cherrytime {
    @Override
    public List<cherrytimeInfo> getcherrytime(){
        String name1 = "cherrytime";
        List<cherrytimeInfo> infos=new ArrayList<>();
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.0.105:5432/xzq", "postgres", "xzq1997");
            Statement statement = connection.createStatement();
            String sqlStr = "SELECT * from cherrytime";
            ResultSet resultset = statement.executeQuery(sqlStr);
            while(resultset.next())
            {
                Date starttime=resultset.getDate("starttime");
                Date endtime=resultset.getDate("endtime");


                //给info对象赋值
                //----------- info.setId
                cherrytimeInfo info=new cherrytimeInfo();
              info.setStarttime(starttime);
              info.setEndtime(endtime);
              infos.add(info);
    }
}

        catch(SQLException var6){
            System.out.println(var6);

        }
        System.out.println(name1);

        return infos;
    }

}
