package cn.edu.whu.tiangeng.domain;

public class siteInfo {
    private String name;
    private String order;
    private String point_x;
    private String point_y;

    public siteInfo() {
    }

    public siteInfo(String name, String point_x, String point_y) {
        this.name = name;
        this.order=order;
        this.point_x = point_x;
        this.point_y = point_y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint_x() {
        return Double.parseDouble(point_x);
    }

    public void setPoint_x(String point_x) {
        this.point_x = point_x;
    }

    public double getPoint_y() {
        return Double.parseDouble(point_y);
    }

    public void setPoint_y(String point_y) {
        this.point_y = point_y;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}