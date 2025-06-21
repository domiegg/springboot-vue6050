package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 售后
 */
@Entity
public class Shouhou {


    //编码

    @Id
    private int id;


    //订单编号

    private String orderno;


    //商品名

    private String spname;


    //商品id

    private int spid;


    //数量

    private int count;


    //价格(元)

    private Double price;


    //申请价格(元)

    private Double totalfee;


    //图片

    private String tupian;


    //状态

    private int state;


    //创建时间

    private Date createtime;


    //原因

    private String reason;


    //退货描述

    private String tdes;


    //订单编号

    private int oid;


    //备注

    private int oiid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Double totalfee) {
        this.totalfee = totalfee;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTdes() {
        return tdes;
    }

    public void setTdes(String tdes) {
        this.tdes = tdes;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getOiid() {
        return oiid;
    }

    public void setOiid(int oiid) {
        this.oiid = oiid;
    }

}