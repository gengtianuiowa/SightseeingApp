package cn.edu.whu.tiangeng.domain;

public class updateInfo {
    private String isupdate;
    private String versionname;
    private int versionid;
    private String downloadurl;

    public updateInfo() {
    }

    public updateInfo(String isupdate, String versionname, int versionid, String downloadurl) {
        this.isupdate = isupdate;
        this.versionname = versionname;
        this.versionid = versionid;
        this.downloadurl = downloadurl;
    }

    public String getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getVersionid() {
        return versionid;
    }

    public void setVersionid(int versionid) {
        this.versionid = versionid;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
}
