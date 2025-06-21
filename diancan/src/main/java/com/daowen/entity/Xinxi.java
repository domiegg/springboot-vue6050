package com.daowen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Xinxi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String tupian;

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    private int lmid;

    public int getLmid() {
        return lmid;
    }

    public void setLmid(int lmid) {
        this.lmid = lmid;
    }

    private int clickcount;

    public int getClickcount() {
        return clickcount;
    }

    public void setClickcount(int clickcount) {
        this.clickcount = clickcount;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pubtime;

    public Date getPubtime() {
        return pubtime;
    }

    public void setPubtime(Date pubtime) {
        this.pubtime = pubtime;
    }

    private int agreecount;
    private int againstcount;

    public int getAgreecount() {
        return agreecount;
    }

    public void setAgreecount(int agreecount) {
        this.agreecount = agreecount;
    }

    public int getAgainstcount() {
        return againstcount;
    }

    public void setAgainstcount(int againstcount) {
        this.againstcount = againstcount;
    }

    private int tuijian;

    public int getTuijian() {
        return tuijian;
    }

    public void setTuijian(int tuijian) {
        this.tuijian = tuijian;
    }

    private String pubren;

    private String dcontent;

    public String getPubren() {
        return pubren;
    }

    public void setPubren(String pubren) {
        this.pubren = pubren;
    }

    public String getDcontent() {
        return dcontent;
    }

    public void setDcontent(String dcontent) {
        this.dcontent = dcontent;
    }

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private int subtypeid;

    public int getSubtypeid() {
        return subtypeid;
    }

    public void setSubtypeid(int subtypeid) {
        this.subtypeid = subtypeid;
    }

    private int tagid;

    public int getTagid() {
        return tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }
    private String fileurl;

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
    private int pbtype;

    public int getPbtype() {
        return pbtype;
    }

    public void setPbtype(int pbtype) {
        this.pbtype = pbtype;
    }
    public String getFileName(){
        String url=this.getFileurl();
        if(url==null||url=="")
            return "";
        return url.substring(url.lastIndexOf("/") + 1);

    }


}
