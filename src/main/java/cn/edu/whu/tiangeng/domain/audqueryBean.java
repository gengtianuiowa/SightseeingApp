package cn.edu.whu.tiangeng.domain;

import cn.edu.whu.tiangeng.services.audqueryService;
import cn.edu.whu.tiangeng.services.audqueryServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class audqueryBean {
    public int audnum(String name) throws SQLException {
        ResultSet r = null;
        try {
            audqueryService a=new audqueryServiceImpl();
            r = a.search(name);
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

    public boolean isaud(String name) throws SQLException {
        ResultSet r = null;
        try {
            audqueryService a=new audqueryServiceImpl();
            r = a.search(name);
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

    public String audurl(String name) throws SQLException {
        ResultSet r = null;
        try {
            audqueryService a=new audqueryServiceImpl();
            r = a.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while(r.next()){
            if(r.getString("type_en").equals("normal_f")){
                String m=r.getString("url");
                return m;
            }
        }
        return "";
    }

    public String audurl_f(String name) throws SQLException {
        ResultSet r = null;
        try {
            audqueryService a=new audqueryServiceImpl();
            r = a.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       while(r.next()){
            if(r.getString("type_en").equals("normal_f")){
                String m=r.getString("url");
                return m;
            }
        }
        return "";
    }

    public String audurl_m(String name) throws SQLException {
        ResultSet r = null;
        try {
            audqueryService a=new audqueryServiceImpl();
            r = a.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while(r.next()){
            if(r.getString("type_en").equals("normal_m")){
                String m=r.getString("url");
                return m;
            }
        }
        return "";


    }

}
