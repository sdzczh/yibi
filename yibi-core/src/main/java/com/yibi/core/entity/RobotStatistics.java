package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RobotStatistics implements Serializable {
    private static final long serialVersionUID = 4294254527730619667L;

    private Integer id;

    private Integer cointype;

    private BigDecimal robotandrobotdealnum;

    private BigDecimal robotandrobotdealamt;

    private BigDecimal robotandpersondealnum;

    private BigDecimal robotandpersondealamt;

    private BigDecimal personandpersondealnum;

    private BigDecimal personandpersondealamt;

    private Date createtime;

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

    public BigDecimal getRobotandrobotdealnum() {
        return robotandrobotdealnum;
    }

    public void setRobotandrobotdealnum(BigDecimal robotandrobotdealnum) {
        this.robotandrobotdealnum = robotandrobotdealnum;
    }

    public BigDecimal getRobotandrobotdealamt() {
        return robotandrobotdealamt;
    }

    public void setRobotandrobotdealamt(BigDecimal robotandrobotdealamt) {
        this.robotandrobotdealamt = robotandrobotdealamt;
    }

    public BigDecimal getRobotandpersondealnum() {
        return robotandpersondealnum;
    }

    public void setRobotandpersondealnum(BigDecimal robotandpersondealnum) {
        this.robotandpersondealnum = robotandpersondealnum;
    }

    public BigDecimal getRobotandpersondealamt() {
        return robotandpersondealamt;
    }

    public void setRobotandpersondealamt(BigDecimal robotandpersondealamt) {
        this.robotandpersondealamt = robotandpersondealamt;
    }

    public BigDecimal getPersonandpersondealnum() {
        return personandpersondealnum;
    }

    public void setPersonandpersondealnum(BigDecimal personandpersondealnum) {
        this.personandpersondealnum = personandpersondealnum;
    }

    public BigDecimal getPersonandpersondealamt() {
        return personandpersondealamt;
    }

    public void setPersonandpersondealamt(BigDecimal personandpersondealamt) {
        this.personandpersondealamt = personandpersondealamt;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
        sb.append(", robotandrobotdealnum=").append(robotandrobotdealnum);
        sb.append(", robotandrobotdealamt=").append(robotandrobotdealamt);
        sb.append(", robotandpersondealnum=").append(robotandpersondealnum);
        sb.append(", robotandpersondealamt=").append(robotandpersondealamt);
        sb.append(", personandpersondealnum=").append(personandpersondealnum);
        sb.append(", personandpersondealamt=").append(personandpersondealamt);
        sb.append(", createtime=").append(createtime);
        sb.append("]");
        return sb.toString();
    }
}