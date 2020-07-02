package cn.edu.whu.tiangeng.services;
import java.sql.*;


public class imgqueryServiceImpl implements imgqueryService{
    public static String URL="jdbc:postgresql://localhost:5432/project";
    public static String USERNAME="postgres";
    public static String PASSWORD="970202";

    public  Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection c=DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return c;
    }

    public  ResultSet search(String a) throws SQLException, ClassNotFoundException{
        Statement stmt= this.connect().createStatement();
        ResultSet r=stmt.executeQuery("select * from imginfo where name_en='"+a+"'or name_cn='"+a+"'");
        return r;
    }
}
