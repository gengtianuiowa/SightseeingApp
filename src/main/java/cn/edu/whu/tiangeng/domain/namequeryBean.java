package cn.edu.whu.tiangeng.domain;

import cn.edu.whu.tiangeng.services.audqueryServiceImpl;
import cn.edu.whu.tiangeng.services.imgqueryServiceImpl;
import cn.edu.whu.tiangeng.services.namequeryServiceImpl;
import cn.edu.whu.tiangeng.services.panoqueryServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class namequeryBean{
    public boolean status(String name) throws SQLException {
        ResultSet r = null;
        try {
            namequeryServiceImpl n=new namequeryServiceImpl();
            r = n.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(r.next()){
            return true;
        }else
            return false;
    }

    public int type(String name) throws SQLException {
        ResultSet r = null;
        try {
            namequeryServiceImpl n=new namequeryServiceImpl();
            r = n.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        r.next();
        return r.getInt("type");
    }

    public float lat(String name) throws SQLException {
        ResultSet r = null;
        try {
            namequeryServiceImpl n=new namequeryServiceImpl();
            r = n.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        r.next();
        return r.getFloat("lat");
    }

    public float lng(String name) throws SQLException {
        ResultSet r = null;
        try {
            namequeryServiceImpl n=new namequeryServiceImpl();
            r = n.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        r.next();
        return r.getFloat("lng");
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

    public boolean isaud(String name) throws SQLException {
        ResultSet r = null;
        try {
            audqueryServiceImpl a=new audqueryServiceImpl();
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

    public boolean ispano(String name) throws SQLException {
        ResultSet r = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            r = p.search(name);
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
}
