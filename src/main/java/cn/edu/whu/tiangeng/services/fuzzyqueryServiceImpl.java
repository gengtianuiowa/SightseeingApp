package cn.edu.whu.tiangeng.services;

import java.sql.*;

public class fuzzyqueryServiceImpl implements fuzzyqueryService{
    public static String URL="jdbc:postgresql://115.159.105.168:5432/whumap";
    public static String USERNAME="postgres";
    public static String PASSWORD="postgre";

    public  Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection c= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return c;
    }

    public  ResultSet search(String a) throws SQLException, ClassNotFoundException{
        Statement stmt= this.connect().createStatement();
        ResultSet r=stmt.executeQuery("select * from annotation where name_en like '%"+a+"%'or name_cn like '%"+a+"%'");
        return r;
    }
}
