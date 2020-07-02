package cn.edu.whu.tiangeng.domain;

import cn.edu.whu.tiangeng.services.panoqueryServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class panoqueryBean {
    public int panonum(String name) throws SQLException {
        ResultSet r = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            r = p.search(name);
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

    public String panourl(String name) throws SQLException {
        ResultSet r = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            r = p.search(name);
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

    public String[] panourls_air(String name) throws SQLException {
        ResultSet r = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            r = p.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        int i=0;
        while(r.next()){
            if(r.getString("type_en").equals("normal_air")){
//                s[i]=r.getString("url");
                i++;
            }
        }
        int j=0;
        String[] s=new String[i];

        ResultSet rs = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            rs = p.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while(rs.next()){
            if(rs.getString("type_en").equals("normal_air")){
                s[j]=rs.getString("url");
                j++;
            }
        }
        return s;
    }

    public String[] panourls_land(String name) throws SQLException {
        ResultSet r = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            r = p.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        int i=0;
        while(r.next()){
            if(r.getString("type_en").equals("normal_land")){
//                s[i]=r.getString("url");
                i++;
            }
        }

        String[] s=new String[i];
        int j=0;

        ResultSet rs = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            rs = p.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        while(rs.next()){
            if(rs.getString("type_en").equals("normal_land")){
                s[j]=rs.getString("url");
                j++;
            }
        }
        return s;
    }

    public String[] panourls_indoor(String name) throws SQLException {
        ResultSet r = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            r = p.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        String[] s=new String[this.panonum(name)];
        int i=0;
        while(r.next()){
            if(r.getString("type_en").equals("normal_indoor")){
//                s[i]=r.getString("url");
                i++;
            }
        }
        String[] s=new String[i];
        int j=0;

        ResultSet rs = null;
        try {
            panoqueryServiceImpl p=new panoqueryServiceImpl();
            rs = p.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        while(rs.next()){
            if(rs.getString("type_en").equals("normal_indoor")){
                s[j]=rs.getString("url");
                j++;
            }
        }
        return s;
    }
}
