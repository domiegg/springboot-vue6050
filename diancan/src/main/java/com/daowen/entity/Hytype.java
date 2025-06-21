package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 会员类型
 */
@Entity
public class Hytype {


    //编码

    @Id
    private int id;


    //名称

    private String name;


    //折扣

    private Double discount;


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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

}