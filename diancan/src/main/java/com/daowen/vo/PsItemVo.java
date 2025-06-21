package com.daowen.vo;

public class PsItemVo {


    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //栏目编号
    private int lmId;
    //栏目名
    private String lmName;
    /**
     * 栏目类型
     */
    private int lmType;


    public int getLmId() {
        return lmId;
    }

    public void setLmId(int lmId) {
        this.lmId = lmId;
    }

    public String getLmName() {
        return lmName;
    }

    public void setLmName(String lmName) {
        this.lmName = lmName;
    }

    public int getLmType() {
        return lmType;
    }

    public void setLmType(int lmType) {
        this.lmType = lmType;
    }


}
