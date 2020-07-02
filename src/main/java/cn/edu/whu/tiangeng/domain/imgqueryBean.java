package cn.edu.whu.tiangeng.domain;

import cn.edu.whu.tiangeng.services.imgqueryServiceImpl;

import java.sql.*;
import java.sql.SQLException;

public class imgqueryBean {


    public int imgnum(String name) throws SQLException {
        ResultSet r = null;
        try {
            imgqueryServiceImpl i=new imgqueryServiceImpl();
            r = i.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int i=0;
        while(r.next()){
            i++;
        }
    return i;
    }

    public boolean isimg(String name) throws SQLException {
        ResultSet r = null;
        try {
            imgqueryServiceImpl i=new imgqueryServiceImpl();
            r = i.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (r.next()){
            return true;
        }else{
            return false;
        }
    }

    public String imgurl(String name) throws SQLException {
        ResultSet r = null;
        try {
            imgqueryServiceImpl i=new imgqueryServiceImpl();
            r = i.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(r.next()){
            String m=r.getString("url");
            return m;
        }else{
            return "";
        }

    }

    public String[] imgurls(String name) throws SQLException {
        ResultSet r = null;
        try {
            imgqueryServiceImpl i=new imgqueryServiceImpl();
            r = i.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String[] s=new String[this.imgnum(name)];
        int i=0;
        while(r.next()){
            s[i]=r.getString("url");
            i++;
        }
        return s;
    }
}
