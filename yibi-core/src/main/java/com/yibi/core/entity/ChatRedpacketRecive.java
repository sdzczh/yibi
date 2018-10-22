package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChatRedpacketRecive implements Serializable {
    private static final long serialVersionUID = -5146957458240283166L;

    private Integer id;

    private Integer userid;

    private Integer redpacid;

    private Integer cointype;

    private BigDecimal amount;

    private BigDecimal amtofcny;

    private Date recivetime;

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

    public Integer getRedpacid() {
        return redpacid;
    }

    public void setRedpacid(Integer redpacid) {
        this.redpacid = redpacid;
    }

    public Integer getCointype() {
        return cointype;
    }

    public void setCointype(Integer cointype) {
        this.cointype = cointype;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmtofcny() {
        return amtofcny;
    }

    public void setAmtofcny(BigDecimal amtofcny) {
        this.amtofcny = amtofcny;
    }

    public Date getRecivetime() {
        return recivetime;
    }

    public void setRecivetime(Date recivetime) {
        this.recivetime = recivetime;
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
        sb.append(", redpacid=").append(redpacid);
        sb.append(", cointype=").append(cointype);
        sb.append(", amount=").append(amount);
        sb.append(", amtofcny=").append(amtofcny);
        sb.append(", recivetime=").append(recivetime);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}