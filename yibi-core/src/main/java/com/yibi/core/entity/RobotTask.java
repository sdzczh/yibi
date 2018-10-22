package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RobotTask implements Serializable {
    private static final long serialVersionUID = -2732724871318756199L;

    private Integer id;

    private Integer cointype;

    private Integer robotid;

    private Integer type;

    private BigDecimal baseprice;

    private Double priceradiomax;

    private Double priceradiomin;

    private BigDecimal numbermax;

    private BigDecimal numbermin;

    private Date starttime;

    private Date endtime;

    private Integer timeinterval;

    private Integer countmax;

    private Integer countmin;

    private Integer excuteuserid;

    private Integer onoff;

    private Integer operid;

    private String jobname;

    private String jobgroupname;

    private String triggername;

    private String triggergroupname;

    private Date createtime;

    private Date updatetime;

    public BigDecimal getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(BigDecimal baseprice) {
        this.baseprice = baseprice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCointype() {
        return cointype;
    }

    public void setCointype(Integer cointype) {
        this.cointype = cointype;
    }

    public Integer getRobotid() {
        return robotid;
    }

    public void setRobotid(Integer robotid) {
        this.robotid = robotid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getPriceradiomax() {
        return priceradiomax;
    }

    public void setPriceradiomax(Double priceradiomax) {
        this.priceradiomax = priceradiomax;
    }

    public Double getPriceradiomin() {
        return priceradiomin;
    }

    public void setPriceradiomin(Double priceradiomin) {
        this.priceradiomin = priceradiomin;
    }

    public BigDecimal getNumbermax() {
        return numbermax;
    }

    public void setNumbermax(BigDecimal numbermax) {
        this.numbermax = numbermax;
    }

    public BigDecimal getNumbermin() {
        return numbermin;
    }

    public void setNumbermin(BigDecimal numbermin) {
        this.numbermin = numbermin;
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

    public Integer getTimeinterval() {
        return timeinterval;
    }

    public void setTimeinterval(Integer timeinterval) {
        this.timeinterval = timeinterval;
    }

    public Integer getCountmax() {
        return countmax;
    }

    public void setCountmax(Integer countmax) {
        this.countmax = countmax;
    }

    public Integer getCountmin() {
        return countmin;
    }

    public void setCountmin(Integer countmin) {
        this.countmin = countmin;
    }

    public Integer getExcuteuserid() {
        return excuteuserid;
    }

    public void setExcuteuserid(Integer excuteuserid) {
        this.excuteuserid = excuteuserid;
    }

    public Integer getOnoff() {
        return onoff;
    }

    public void setOnoff(Integer onoff) {
        this.onoff = onoff;
    }

    public Integer getOperid() {
        return operid;
    }

    public void setOperid(Integer operid) {
        this.operid = operid;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname == null ? null : jobname.trim();
    }

    public String getJobgroupname() {
        return jobgroupname;
    }

    public void setJobgroupname(String jobgroupname) {
        this.jobgroupname = jobgroupname == null ? null : jobgroupname.trim();
    }

    public String getTriggername() {
        return triggername;
    }

    public void setTriggername(String triggername) {
        this.triggername = triggername == null ? null : triggername.trim();
    }

    public String getTriggergroupname() {
        return triggergroupname;
    }

    public void setTriggergroupname(String triggergroupname) {
        this.triggergroupname = triggergroupname == null ? null : triggergroupname.trim();
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
        sb.append(", cointype=").append(cointype);
        sb.append(", robotid=").append(robotid);
        sb.append(", type=").append(type);
        sb.append(", priceradiomax=").append(priceradiomax);
        sb.append(", priceradiomin=").append(priceradiomin);
        sb.append(", numbermax=").append(numbermax);
        sb.append(", numbermin=").append(numbermin);
        sb.append(", starttime=").append(starttime);
        sb.append(", endtime=").append(endtime);
        sb.append(", timeinterval=").append(timeinterval);
        sb.append(", countmax=").append(countmax);
        sb.append(", countmin=").append(countmin);
        sb.append(", excuteuserid=").append(excuteuserid);
        sb.append(", onoff=").append(onoff);
        sb.append(", operid=").append(operid);
        sb.append(", jobname=").append(jobname);
        sb.append(", jobgroupname=").append(jobgroupname);
        sb.append(", triggername=").append(triggername);
        sb.append(", triggergroupname=").append(triggergroupname);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}