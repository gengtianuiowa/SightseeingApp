package cn.edu.whu.tiangeng.domain;

import java.util.Date;

public class cherrytimeInfo {
    private Date starttime;
    private Date endtime;

    public cherrytimeInfo() {

    }

    public cherrytimeInfo(Date starttime, Date endtime) {
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
