package cn.edu.whu.tiangeng.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface busrouteService {
    public Connection connect()throws ClassNotFoundException, SQLException;
    public ResultSet search(String a)throws SQLException, ClassNotFoundException;
    public double[] des_cor(String end) throws SQLException, ClassNotFoundException;
    public JSONObject searchroute_w(double lng, double lat, double lng_d, double lat_d, int how) throws SQLException, ClassNotFoundException;
    public JSONObject stationgeojson(double lng,double lat) throws SQLException, ClassNotFoundException;
    public int stationnum(double []a,double[]b)throws SQLException, ClassNotFoundException;
    public JSONArray stationnamelist(double []a, double[]b)throws SQLException, ClassNotFoundException;
    public JSONObject busroutegeojson(double[] sc,double[] ec) throws SQLException, ClassNotFoundException;
}
