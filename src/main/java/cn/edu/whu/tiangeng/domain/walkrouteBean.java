package cn.edu.whu.tiangeng.domain;

import cn.edu.whu.tiangeng.services.walkrouteServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class walkrouteBean {
    public boolean status(String name) throws SQLException {
        ResultSet r = null;
        try {
            walkrouteServiceImpl w=new walkrouteServiceImpl();
            r = w.search(name);
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
