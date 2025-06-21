package com.daowen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.persistence.*;

/**
 * 优惠券
 */

public class Coupon {


    //编码
    private int id;

    //名字
    private String name;

    //面值（元）
    private int fee;

    //最小金额要求(元)
    private int minreq;

    //创建时间
    private Date createtime;

    //过期日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date overdate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getMinreq() {
        return minreq;
    }

    public void setMinreq(int minreq) {
        this.minreq = minreq;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getOverdate() {
        return overdate;
    }

    public void setOverdate(Date overdate) {
        this.overdate = overdate;
    }

}