package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    private static final long serialVersionUID = 4179228179216221696L;

    private Integer id;

    private String title;

    private String pic;

    private String roundup;

    private Integer type;

    private Integer state;

    private Integer createid;

    private Integer operid;

    private Date createtime;

    private Date updatetime;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getRoundup() {
        return roundup;
    }

    public void setRoundup(String roundup) {
        this.roundup = roundup == null ? null : roundup.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCreateid() {
        return createid;
    }

    public void setCreateid(Integer createid) {
        this.createid = createid;
    }

    public Integer getOperid() {
        return operid;
    }

    public void setOperid(Integer operid) {
        this.operid = operid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", pic=").append(pic);
        sb.append(", roundup=").append(roundup);
        sb.append(", type=").append(type);
        sb.append(", state=").append(state);
        sb.append(", createid=").append(createid);
        sb.append(", operid=").append(operid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}