package com.daowen.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Shorder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String ddno;

    public String getDdno() {
        return ddno;
    }

    public void setDdno(String ddno) {
        this.ddno = ddno;
    }

    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    private int purchaser;

    public int getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(int purchaser) {
        this.purchaser = purchaser;
    }

    private String psstyle;

    public String getPsstyle() {
        return psstyle;
    }

    public void setPsstyle(String psstyle) {
        this.psstyle = psstyle;
    }

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private int  addid;

    public int getAddid() {
        return addid;
    }

    public void setAddid(int addid) {
        this.addid = addid;
    }

    private int state;
    private Double totalfee;

    private int zyid;

    public int getZyid() {
        return zyid;
    }

    public void setZyid(int zyid) {
        this.zyid = zyid;
    }

    private Double originfee;

    public Double getOriginfee() {
        return originfee;
    }

    public void setOriginfee(Double originfee) {
        this.originfee = originfee;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Double getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Double totalfee) {
        this.totalfee = totalfee;
    }

}
