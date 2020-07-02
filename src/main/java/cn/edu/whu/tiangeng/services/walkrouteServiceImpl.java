package cn.edu.whu.tiangeng.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class walkrouteServiceImpl implements walkrouteService{
    public static String URL="jdbc:postgresql://localhost:5432/project";
    public static String USERNAME="postgres";
    public static String PASSWORD="970202";
    public static String RDATABASE="road";
    public static String PDATABASE="road_vertices_pgr";
    public static double TIME=0;

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


    public JSONObject searchroute(double lng,double lat,String end) throws SQLException, ClassNotFoundException {
        //查询终点对应的点id
        Class.forName("org.postgresql.Driver");
        Connection outconnect= DriverManager.getConnection("jdbc:postgresql://115.159.105.168:5432/whumap","postgres","postgre");
        Statement outstmt = outconnect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet r_endcor = outstmt.executeQuery("select * from annotation where name_en='" + end + "'or name_cn='" + end + "'");
        if(!(r_endcor.next())){
            return null;
        }
        r_endcor.first();
        Double endlng = r_endcor.getDouble("lng");
        Double endlat=r_endcor.getDouble("lat");
        Statement stmt=this.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet r_end = stmt.executeQuery("SELECT id::integer,st_asgeojson(the_geom) as geojson FROM "+PDATABASE+"     \n" +
                    "            ORDER BY the_geom <-> ST_GeometryFromText('POINT('     \n" +
                    "            || "+endlng+" || ' ' || "+endlat+" || ')') LIMIT 1;");
        r_end.next();
        int endId = r_end.getInt("id");
        String endgeojson=r_end.getString("geojson");

        //查询起点对应的点id
        ResultSet r_start = stmt.executeQuery("SELECT id::integer,st_asgeojson(the_geom) as geojson FROM "+PDATABASE+"    \n" +
                "            ORDER BY the_geom <-> ST_GeometryFromText('POINT('     \n" +
                "            || "+lng+" || ' ' || "+lat+" || ')') LIMIT 1;");
        r_start.next();
        int startId = r_start.getInt("id");
        String startgeojson=r_start.getString("geojson");

        //查询最短路径（用dijkstra算法）
        ResultSet r_route = stmt.executeQuery("SELECT seq, id1 AS node, id2 AS edge, cost FROM pgr_dijkstra('\n" +
                "                SELECT  gid AS id,\n" +
                "                         source::integer,\n" +
                "                         target::integer,\n" +
                "                         length::double precision AS cost\n" +
                "                        FROM public."+RDATABASE+"',\n" +
                "                           " + startId + "," + endId +
                "                   , false, false);");

        //计算路径中节点个数
        r_route.last();
        int edgenumber=r_route.getRow();

        //生成路径的geojson
        r_route.first();
        int []edge=new int[edgenumber];
        int []point=new int[edgenumber];
        double length=0,time=0;
        int i=0;
        edge[i]=r_route.getInt(3);
        point[i]=r_route.getInt(2);
        length=length+r_route.getDouble(4);
        i++;
        while(r_route.next()){
            //获取边对应的序号（gid）
            edge[i]=r_route.getInt(3);//边是edge[0]到edge[行数-2]，最后一个一定是-1，不会使用但还是录入。
            point[i]=r_route.getInt(2);
            length=length+r_route.getDouble(4);
            i++;
        }
        TIME=length/4;

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
        String []edgegeojson=new String[edgenumber];
        for(int j=0;j<edgenumber;j++){
            ResultSet r_edgegeojson = stmt.executeQuery("select st_asgeojson(the_geom) from "+PDATABASE+" where id=" + point[j] );
            r_edgegeojson.next();
            edgegeojson[j]=r_edgegeojson.getString(1);
//            edgegeojson[j]=edgegeojson[j].substring(30,edgegeojson[j].length()-1);//求长度
        }

        //拼接成完整的geojson
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","MultiLineString");
//        geojson=geojson+startgeojson.substring(30,startgeojson.length()-1);
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
}
