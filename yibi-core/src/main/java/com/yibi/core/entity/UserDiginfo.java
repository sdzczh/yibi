package com.yibi.core.entity;

import com.yibi.common.utils.TimeStampUtils;

import java.io.Serializable;
import java.util.Date;

public class UserDiginfo implements Serializable {
    private static final long serialVersionUID = 7080238461353424019L;

    private Integer id;

    private Integer userid;

    private Boolean digflag;

    private Date starttime;

    private Integer digcalcul;

    private Integer logindays;

    private Date lasttime;

    private Boolean dayrewardstate;

    private Boolean tenrewardstate;

    private Boolean monthrewardstate;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Boolean getDigflag() {
        return digflag;
    }

    public void setDigflag(Boolean digflag) {
        this.digflag = digflag;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getDigcalcul() {
        return digcalcul;
    }

    public void setDigcalcul(Integer digcalcul) {
        this.digcalcul = digcalcul;
    }

    public Integer getLogindays() {
        return logindays;
    }

    public void setLogindays(Integer logindays) {
        this.logindays = logindays;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public Boolean getDayrewardstate() {
        return dayrewardstate;
    }

    public void setDayrewardstate(Boolean dayrewardstate) {
        this.dayrewardstate = dayrewardstate;
    }

    public Boolean getTenrewardstate() {
        return tenrewardstate;
    }

    public void setTenrewardstate(Boolean tenrewardstate) {
        this.tenrewardstate = tenrewardstate;
    }

    public Boolean getMonthrewardstate() {
        return monthrewardstate;
    }

    public void setMonthrewardstate(Boolean monthrewardstate) {
        this.monthrewardstate = monthrewardstate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", digflag=").append(digflag);
        sb.append(", starttime=").append(starttime);
        sb.append(", digcalcul=").append(digcalcul);
        sb.append(", logindays=").append(logindays);
        sb.append(", lasttime=").append(lasttime);
        sb.append(", dayrewardstate=").append(dayrewardstate);
        sb.append(", tenrewardstate=").append(tenrewardstate);
        sb.append(", monthrewardstate=").append(monthrewardstate);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }

    public UserDiginfo(){
        this.digflag = false;
        this.digcalcul = 0;
        this.logindays = 0;
        this.dayrewardstate = false;
        this.tenrewardstate = false;
        this.monthrewardstate = false;
        this.lasttime = TimeStampUtils.getSomeDayTime(-2);
    }
}