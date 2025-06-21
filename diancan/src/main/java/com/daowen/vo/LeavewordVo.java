package com.daowen.vo;

import com.daowen.entity.Leaveword;

public class LeavewordVo extends Leaveword {

    private String pubren;
    private String pbrname;
    private String pbrphoto;
    private String rpname;
    private String rpphoto;




    public String getRpphoto() {
        return rpphoto;
    }

    public void setRpphoto(String rpphoto) {
        this.rpphoto = rpphoto;
    }

    public String getPubren() {
        return pubren;
    }

    public void setPubren(String pubren) {
        this.pubren = pubren;
    }

    public String getPbrname() {
        return pbrname;
    }

    public void setPbrname(String pbrname) {
        this.pbrname = pbrname;
    }

    public String getPbrphoto() {
        return pbrphoto;
    }

    public void setPbrphoto(String pbrphoto) {
        this.pbrphoto = pbrphoto;
    }

    public String getRpname() {
        return rpname;
    }

    public void setRpname(String rpname) {
        this.rpname = rpname;
    }
}
