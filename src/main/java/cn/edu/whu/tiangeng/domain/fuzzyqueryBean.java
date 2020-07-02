package cn.edu.whu.tiangeng.domain;

import cn.edu.whu.tiangeng.services.fuzzyqueryServiceImpl;
import org.json.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;

public class fuzzyqueryBean {
    public JSONArray namelist(String name) throws SQLException {
        ResultSet r = null;
        try {
            fuzzyqueryServiceImpl f=new fuzzyqueryServiceImpl();
            r = f.search(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONArray a=new JSONArray();
//        String[] s=new String[this.num(name)];
        int j=0;
        while(r.next()){
//            s[j]=r.getString("name_cn");
            a.put(j,r.getString("name_cn"));
            j++;
        }
        return a;
    }

    public int num(String name) throws SQLException {
        ResultSet r = null;
        try {
            fuzzyqueryServiceImpl f=new fuzzyqueryServiceImpl();
            r = f.search(name);
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
}
