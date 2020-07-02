package cn.edu.whu.tiangeng.domain;

public class emergencyInfo {
    private String id;
    private String name;
    private String reason;
    private String phone;

    public emergencyInfo() {
    }

    public emergencyInfo(String id, String name, String reason, String phone) {
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
