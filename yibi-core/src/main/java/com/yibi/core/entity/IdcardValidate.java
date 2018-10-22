package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class IdcardValidate implements Serializable {
    private static final long serialVersionUID = 4926271479744271712L;

    private Integer id;

    private Integer userid;

    private String name;

    private String identificationnumber;

    private String idcardtype;

    private String idcardexpiry;

    private String address;

    private String sex;

    private String idcardfrontpic;

    private String idcardbackpic;

    private String facepic;

    private Integer state;

    private String taskid;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdentificationnumber() {
        return identificationnumber;
    }

    public void setIdentificationnumber(String identificationnumber) {
        this.identificationnumber = identificationnumber == null ? null : identificationnumber.trim();
    }

    public String getIdcardtype() {
        return idcardtype;
    }

    public void setIdcardtype(String idcardtype) {
        this.idcardtype = idcardtype == null ? null : idcardtype.trim();
    }

    public String getIdcardexpiry() {
        return idcardexpiry;
    }

    public void setIdcardexpiry(String idcardexpiry) {
        this.idcardexpiry = idcardexpiry == null ? null : idcardexpiry.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getIdcardfrontpic() {
        return idcardfrontpic;
    }

    public void setIdcardfrontpic(String idcardfrontpic) {
        this.idcardfrontpic = idcardfrontpic == null ? null : idcardfrontpic.trim();
    }

    public String getIdcardbackpic() {
        return idcardbackpic;
    }

    public void setIdcardbackpic(String idcardbackpic) {
        this.idcardbackpic = idcardbackpic == null ? null : idcardbackpic.trim();
    }

    public String getFacepic() {
        return facepic;
    }

    public void setFacepic(String facepic) {
        this.facepic = facepic == null ? null : facepic.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
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
        sb.append(", name=").append(name);
        sb.append(", identificationnumber=").append(identificationnumber);
        sb.append(", idcardtype=").append(idcardtype);
        sb.append(", idcardexpiry=").append(idcardexpiry);
        sb.append(", address=").append(address);
        sb.append(", sex=").append(sex);
        sb.append(", idcardfrontpic=").append(idcardfrontpic);
        sb.append(", idcardbackpic=").append(idcardbackpic);
        sb.append(", facepic=").append(facepic);
        sb.append(", state=").append(state);
        sb.append(", taskid=").append(taskid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}