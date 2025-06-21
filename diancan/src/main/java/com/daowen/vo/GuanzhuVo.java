package com.daowen.vo;

import com.daowen.entity.Huiyuan;

public class GuanzhuVo extends Huiyuan {

    private int gzid;

    private int targetid;
    private  int actionid;

    public int getGzid() {
        return gzid;
    }

    public void setGzid(int gzid) {
        this.gzid = gzid;
    }

    public int getTargetid() {
        return targetid;
    }

    public void setTargetid(int targetid) {
        this.targetid = targetid;
    }

    public int getActionid() {
        return actionid;
    }

    public void setActionid(int actionid) {
        this.actionid = actionid;
    }
}
