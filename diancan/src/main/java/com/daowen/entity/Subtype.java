package com.daowen.entity;

/**
 * 子栏目
 */

public class Subtype {


    //编码
    private int id;

    //名称
    private String name;

    //父编号
    private int parentid;


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

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

}