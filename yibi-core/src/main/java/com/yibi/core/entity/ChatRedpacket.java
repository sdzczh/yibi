package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChatRedpacket implements Serializable {
    private static final long serialVersionUID = -3132926231788680135L;

    private Integer id;

    private Integer senduserid;

    private Integer accounttype;

    private Integer type;

    private Integer cointype;

    private BigDecimal amount;

    private Integer num;

    private BigDecimal amtofcny;

    private BigDecimal remainamt;

    private Integer remainnum;

    private Integer state;

    private String note;

    private Date inactivetime;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenduserid() {
        return senduserid;
    }

    public void setSenduserid(Integer senduserid) {
        this.senduserid = senduserid;
    }

    public Integer getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(Integer accounttype) {
        this.accounttype = accounttype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getAmtofcny() {
        return amtofcny;
    }

    public void setAmtofcny(BigDecimal amtofcny) {
        this.amtofcny = amtofcny;
    }

    public BigDecimal getRemainamt() {
        return remainamt;
    }

    public void setRemainamt(BigDecimal remainamt) {
        this.remainamt = remainamt;
    }

    public Integer getRemainnum() {
        return remainnum;
    }

    public void setRemainnum(Integer remainnum) {
        this.remainnum = remainnum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getInactivetime() {
        return inactivetime;
    }

    public void setInactivetime(Date inactivetime) {
        this.inactivetime = inactivetime;
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
        sb.append(", senduserid=").append(senduserid);
        sb.append(", accounttype=").append(accounttype);
        sb.append(", type=").append(type);
        sb.append(", cointype=").append(cointype);
        sb.append(", amount=").append(amount);
        sb.append(", num=").append(num);
        sb.append(", amtofcny=").append(amtofcny);
        sb.append(", remainamt=").append(remainamt);
        sb.append(", remainnum=").append(remainnum);
        sb.append(", state=").append(state);
        sb.append(", note=").append(note);
        sb.append(", inactivetime=").append(inactivetime);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}