package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class ChatGroupUser implements Serializable {
    private static final long serialVersionUID = -3958990985400291408L;

    private Integer id;

    private Integer userid;

    private Integer talkgroupid;

    private Integer role;

    private String remark;

    private Integer state;

    private Boolean ismuted;

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

    public Integer getTalkgroupid() {
        return this.talkgroupid;
    }

    public void setTalkgroupid(Integer talkgroupid) {
        this.talkgroupid = talkgroupid;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getIsmuted() {
        return ismuted;
    }

    public void setIsmuted(Boolean ismuted) {
        this.ismuted = ismuted;
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
        sb.append(", talkgroupid=").append(talkgroupid);
        sb.append(", role=").append(role);
        sb.append(", remark=").append(remark);
        sb.append(", state=").append(state);
        sb.append(", ismuted=").append(ismuted);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}