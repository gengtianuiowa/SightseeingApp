package cn.edu.whu.tiangeng.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface fuzzyqueryService {
    public Connection connect()throws ClassNotFoundException, SQLException;
    public ResultSet search(String a)throws SQLException, ClassNotFoundException;
}
