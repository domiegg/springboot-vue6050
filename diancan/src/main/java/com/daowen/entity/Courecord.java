package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 领劵
 */

public class Courecord {


    //编码
    private int id;

    //用户
    private int hyid;

    //劵编号
    private int couponid;

    //状态
    private int state;

    //领取时间
    private Date createtime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHyid() {
        return hyid;
    }

    public void setHyid(int hyid) {
        this.hyid = hyid;
    }

    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
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

}