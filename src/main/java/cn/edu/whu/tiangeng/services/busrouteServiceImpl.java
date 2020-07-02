package cn.edu.whu.tiangeng.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class busrouteServiceImpl implements busrouteService{
    public static String URL="jdbc:postgresql://localhost:5432/project";
    public static String USERNAME="postgres";
    public static String PASSWORD="970202";
    public static String BUS_R="schoolbus";
    public static String BUS_RPOINT="schoolbus_vertices_pgr";
    public static String BUS_P="schoolbuspoint";
    public static String WALK_R="road";
    public static String WALK_P="road_vertices_pgr";
    public static double[] TIME=new double[3];

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection c= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return c;
    }

    public ResultSet search(String a) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection outconnect= DriverManager.getConnection("jdbc:postgresql://115.159.105.168:5432/whumap","postgres","postgre");
        Statement stmt = outconnect.createStatement();
        ResultSet r = stmt.executeQuery("select * from annotation where name_en='" + a + "'or name_cn='" + a + "'");
        return r;
    }

    public JSONObject searchroute_w(double lng,double lat,double lng_d,double lat_d,int how) throws SQLException, ClassNotFoundException {
        //查询终点对应的点id
        Statement stmt = this.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet r_end = stmt.executeQuery("SELECT id::integer,st_asgeojson(the_geom) as geojson FROM " + WALK_P + "     \n" +
                "            ORDER BY the_geom <-> ST_GeometryFromText('POINT('     \n" +
                "            || " + lng_d + " || ' ' || " + lng_d + " || ')') LIMIT 1;");
        r_end.next();
        int endId = r_end.getInt("id");

        //查询起点对应的点id
        ResultSet r_start = stmt.executeQuery("SELECT id::integer,st_asgeojson(the_geom) as geojson FROM " + WALK_P + "    \n" +
                "            ORDER BY the_geom <-> ST_GeometryFromText('POINT('     \n" +
                "            || " + lng + " || ' ' || " + lat + " || ')') LIMIT 1;");
        r_start.next();
        int startId = r_start.getInt("id");
        String startgeojson = r_start.getString("geojson");

        //查询最短路径（用dijkstra算法）
        ResultSet r_route = stmt.executeQuery("SELECT seq, id1 AS node, id2 AS edge, cost FROM pgr_dijkstra('\n" +
                "                SELECT  gid AS id,\n" +
                "                         source::integer,\n" +
                "                         target::integer,\n" +
                "                         length::double precision AS cost\n" +
                "                        FROM public." + WALK_R + "',\n" +
                "                           " + startId + "," + endId +
                "                   , false, false);");

        //计算路径中节点个数
        r_route.last();
        int edgenumber = r_route.getRow();

        //生成路径的geojson
        r_route.first();
        int[] edge = new int[edgenumber];
        int[] point = new int[edgenumber];
        double length = 0, time = 0;
        int i = 0;
        edge[i] = r_route.getInt(3);
        point[i] = r_route.getInt(2);
        length = length + r_route.getDouble(4);
        i++;
        while (r_route.next()) {
            //获取边对应的序号（gid）
            edge[i] = r_route.getInt(3);//边是edge[0]到edge[行数-2]，最后一个一定是-1，不会使用但还是录入。
            point[i] = r_route.getInt(2);
            length = length + r_route.getDouble(4);
            i++;
        }
        TIME[how] = length / 4;

//        String []edgegeojson=new String[edgenumber-1];
//
//        for(int j=0;j<edgenumber-1;j++){
//            ResultSet r_edgelength = stmt.executeQuery("select cost from test_data_vertices where node=" + point[j] );
//            r_point.next();
//            edgegeojson[j]=r_point.getString(1);
//            ResultSet r_edgelength = stmt.executeQuery("select length from test_data where gid=" + edge[j] );
//            r_edgelength.next();
//            edgegeojson[j]=edgegeojson[j].substring(30,edgegeojson[j].length());//求长度
//            length=length+r_edgelength.getDouble(4);
//        }
//        TIME=length/4;
        //获取每个字段的geojson
        String[] edgegeojson = new String[edgenumber];
        for (int j = 0; j < edgenumber; j++) {
            ResultSet r_edgegeojson = stmt.executeQuery("select st_asgeojson(the_geom) from " + WALK_P + " where id=" + point[j]);
            r_edgegeojson.next();
            edgegeojson[j] = r_edgegeojson.getString(1);
//            edgegeojson[j] = edgegeojson[j].substring(30, edgegeojson[j].length() - 1);//求长度
        }

        //拼接成完整的geojson
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "MultiLineString");
//        geojson=geojson+startgeojson.substring(30,startgeojson.length()-1);
//        for(int j=0;j<edgenumber;j++){
//            geojson=geojson+","+edgegeojson[j];
//        }
//        geojson="["+geojson+"]";
//        jsonObject.put("coordinates",geojson);
//        return jsonObject.toString();
        //将string当中的坐标提取出来，存成jsonarray
        //查逗号在的位置
        int a = startgeojson.indexOf(",");
        a=startgeojson.indexOf(",",a+1);
        //创建json
        JSONArray coordinateJson= new JSONArray();
        double point_x=lng;
        double point_y=lat;
        JSONArray array= new JSONArray();
        array.put(0,point_x);
        array.put(1,point_y);
        coordinateJson.put(0,array);
        for(int j=0;j<edgenumber;j++){
            int a1 = edgegeojson[j].indexOf(",");
            a1=edgegeojson[j].indexOf(",",a1+1);
            double point_x1=Double.parseDouble(edgegeojson[j].substring(31,a1-1));
            double point_y1=Double.parseDouble(edgegeojson[j].substring(a1-1+2,edgegeojson[j].length()-2));
            JSONArray array1= new JSONArray();
            array1.put(0,point_x1);
            array1.put(1,point_y1);
            coordinateJson.put(j+1,array1);
        }
        jsonObject.put("coordinates",coordinateJson);
        return jsonObject;
    }

    public JSONObject stationgeojson(double lng,double lat) throws SQLException, ClassNotFoundException{
        //获取站的geojson
        Statement stmt=this.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet r_start = stmt.executeQuery("SELECT st_asgeojson(geom) as geojson FROM "+BUS_P+"    \n" +
                "            ORDER BY geom <-> ST_GeometryFromText('POINT('     \n" +
                "            || "+lng+" || ' ' || "+lat+" || ')') LIMIT 1;");
        r_start.next();
        String geojson=r_start.getString("geojson");
        //查逗号在的位置
        JSONObject object=new JSONObject();
        object.put("type","point");
        int a = geojson.indexOf(",");
        a=geojson.indexOf(",",a+1);
        //创建json
        double point_x=Double.parseDouble(geojson.substring(31,a-1));;
        double point_y=Double.parseDouble(geojson.substring(a+1,geojson.length()-2));
        JSONArray array= new JSONArray();
        array.put(0,point_x);
        array.put(1,point_y);
        object.put("coordinates",array);
        return object;
    }

    public double[] findstation(double lng,double lat) throws SQLException, ClassNotFoundException{
        Statement stmt=this.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet r_start = stmt.executeQuery("SELECT lng,lat FROM "+BUS_P+"    \n" +
                "            ORDER BY geom <-> ST_GeometryFromText('POINT('     \n" +
                "            || "+lng+" || ' ' || "+lat+" || ')') LIMIT 1;");
        r_start.next();
        double stationlng = r_start.getDouble("lng");
        double stationlat = r_start.getDouble("lat");
        double []station=new double[2];
        station[0]=stationlng;
        station[1]=stationlat;
        return station;
    }

    public double[] des_cor(String end) throws SQLException, ClassNotFoundException{
        double[] des=new double[2];
        Class.forName("org.postgresql.Driver");
        Connection outconnect= DriverManager.getConnection("jdbc:postgresql://115.159.105.168:5432/whumap","postgres","postgre");
        Statement outstmt = outconnect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet r_endcor = outstmt.executeQuery("select * from annotation where name_en='" + end + "'or name_cn='" + end + "'");
        if(!(r_endcor.next())){
            return des;
        }
        r_endcor.first();
        Double endlng = r_endcor.getDouble("lng");
        Double endlat=r_endcor.getDouble("lat");
        des[0]=endlng;
        des[1]=endlat;
        return des;
    }

    public int stationnum(double []a,double[]b)throws SQLException, ClassNotFoundException{
        //输入起终点的经纬度，得到对应的序号，再算出数量
        Statement stmt=this.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        //得到起点序号
        ResultSet s_station = stmt.executeQuery("SELECT seq FROM "+BUS_P+" where lng="+a[0]+" and lat="+a[1]);
        s_station.next();
        int s=s_station.getInt("seq");

        //得到终点序号
        ResultSet e_station = stmt.executeQuery("SELECT seq FROM "+BUS_P+" where lng="+b[0]+" and lat="+b[1]);
        e_station.next();
        int e=e_station.getInt("seq");

        //算seq的差值，分类讨论
        int num;
        if(s<=e){
            num=e-s+1;
        }else{
            num=(24-s)+e+1;
        }
        return num;
    }

    public JSONArray stationnamelist(double []a,double[]b)throws SQLException, ClassNotFoundException {
        //输入起终点的经纬度，得到对应的序号，再将名称存在数组里
        Statement stmt = this.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //得到起点序号
        ResultSet s_station = stmt.executeQuery("SELECT seq FROM " + BUS_P + " where lng=" + a[0] + " and lat=" + a[1]);
        s_station.next();
        int s = s_station.getInt("seq");

        //得到终点序号
        ResultSet e_station = stmt.executeQuery("SELECT seq FROM " + BUS_P + " where lng=" + b[0] + " and lat=" + b[1]);
        e_station.next();
        int e = e_station.getInt("seq");

        //求名字清单，存在数组n[]里
        //求站数
        int num;
        if (s <= e) {
            num = e - s+1;
        } else {
            num = (24 - s) + e+1;
        }

        //求名字的数组
        if (s == e) {
            //拼接json
            JSONArray json=new JSONArray();
            return json;
        } else if (s < e) {
            String[] n = new String[num];
            ResultSet s_station2 = stmt.executeQuery("SELECT name FROM " + BUS_P + " where seq>=" + s + "and seq<=" + e);
            int i = 0;
            while (s_station2.next()) {
                n[i] = s_station2.getString("name");
                i++;
            }

            //拼接json
            JSONArray json = new JSONArray();
//            json = json + "[\"" + n[0];
            for (int j = 0; j < num; j++) {
                int a1 = n[j].indexOf(",");
                a1=n[j].indexOf(",",a1+1);
                String name=n[j];
                json.put(j,name);
            }
//            json = json + "\"]";
            return json;
        }  else {
            String[] n = new String[num];
            ResultSet s_station2 = stmt.executeQuery("SELECT name,seq FROM " + BUS_P + " where seq>=" + s + "and seq<=24 or seq<=" + e + "order by seq");
            int i = 0;
            while (s_station2.next()) {
                n[i] = s_station2.getString("name");
                i++;
            }
            //拼接json
            JSONArray json = new JSONArray();
//            json = json + "[\"" + n[0];
            for (int j = 0; j < num; j++) {
                int a1 = n[j].indexOf(",");
                a1=n[j].indexOf(",",a1+1);
                String name=n[j];
                json.put(j,name);
            }
//            json = json + "\"]";
            return json;
        }
    }

    public JSONObject busroutegeojson(double[] sc,double[] ec) throws SQLException, ClassNotFoundException{
        //输入起终点的经纬度，得到对应的序号，再将名称存在数组里
        Statement stmt = this.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //得到起点序号
        ResultSet s_station = stmt.executeQuery("SELECT seq FROM " + BUS_P + " where lng=" + sc[0] + " and lat=" + sc[1]);
        s_station.next();
        int s = s_station.getInt("seq");

        //得到终点序号
        ResultSet e_station = stmt.executeQuery("SELECT seq FROM " + BUS_P + " where lng=" + ec[0] + " and lat=" + ec[1]);
        e_station.next();
        int e = e_station.getInt("seq");

        //求名字清单，存在数组n[]里
        //求站数
        int num;
        if (s <= e) {
            num = e - s+1;
        } else {
            num = (24 - s) + e+1;
        }

        //求geojson的数组
        if (s == e) {
            //拼接geojson
            JSONObject geojson = new JSONObject();
            geojson.put("tyep","");
            JSONArray array1=new JSONArray();
            geojson.put("coordinates",array1);
            return geojson;
        } else if (s < e) {
            String[] n = new String[num];
            ResultSet s_station2 = stmt.executeQuery("SELECT st_asgeojson(geom) FROM " + BUS_P + " where seq>=" + s + "and seq<=" + e);
            int i = 0;
            while (s_station2.next()) {
                n[i] = s_station2.getString(1);
//                geojson=""+tartgeojson.substring(30,startgeojson.length()-1)
                i++;
            }

            //拼接geojson
            JSONObject geojson = new JSONObject();
            JSONArray coordinateJson=new JSONArray();
            for(int j=0;j<num;j++) {
                int a1 = n[j].indexOf(",");
                a1=n[j].indexOf(",",a1+1);
                double point_x1=Double.parseDouble(n[j].substring(31,a1-1));
                double point_y1=Double.parseDouble(n[j].substring(a1-1+2,n[j].length()-2));
                JSONArray array1= new JSONArray();
                array1.put(0,point_x1);
                array1.put(1,point_y1);
                coordinateJson.put(1,array1);
            }
            return geojson;
        }  else {
            //为了按照顺序存储geojson，分开写
            String[] n = new String[num];
            ResultSet s_station2 = stmt.executeQuery("SELECT st_asgeojson(geom) FROM " + BUS_P + " where seq>=" + s + " order by seq");
            int i = 0;
            while (s_station2.next()) {
                n[i] = s_station2.getString(1);
                i++;
            }
            ResultSet s_station2extra = stmt.executeQuery("SELECT st_asgeojson(geom) FROM " + BUS_P + " where seq<=" + e + " order by seq");
            while (s_station2extra.next()) {
                n[i] = s_station2extra.getString(1);
                i++;
            }
            //拼接json
            JSONObject geojson = new JSONObject();
            JSONArray coordinateJson=new JSONArray();
            for(int j=0;j<num;j++) {
                int a1 = n[j].indexOf(",");
                a1=n[j].indexOf(",",a1+1);
                double point_x1=Double.parseDouble(n[j].substring(31,a1-1));
                double point_y1=Double.parseDouble(n[j].substring(a1-1+2,n[j].length()-2));
                JSONArray array1= new JSONArray();
                array1.put(0,point_x1);
                array1.put(1,point_y1);
                coordinateJson.put(j,array1);
            }
            return geojson;
        }
    }
}
