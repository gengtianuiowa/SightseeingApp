package cn.edu.whu.tiangeng.services;

import org.json.JSONObject;

import java.sql.*;

public interface walkrouteService {
    public Connection connect()throws ClassNotFoundException, SQLException;
    public ResultSet search(String a)throws SQLException, ClassNotFoundException;
//    public ResultSet searchlength(String a) throws SQLException, ClassNotFoundException;
    public JSONObject searchroute(double lng, double lat, String end) throws SQLException, ClassNotFoundException;
}
