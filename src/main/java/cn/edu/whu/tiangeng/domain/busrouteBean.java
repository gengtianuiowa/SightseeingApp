package cn.edu.whu.tiangeng.domain;

import cn.edu.whu.tiangeng.services.busrouteServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class busrouteBean {
    public boolean status(String name) throws SQLException {
        ResultSet r = null;
        try {
            busrouteServiceImpl b=new busrouteServiceImpl();
            r = b.search(name);
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
