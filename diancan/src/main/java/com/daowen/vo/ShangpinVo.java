package com.daowen.vo;

import com.daowen.entity.Shangpin;

import java.util.Arrays;
import java.util.List;


public class ShangpinVo extends Shangpin {

    private List<String> images;
    public  List<String> getImages(){
        if(getTupian()==null)
            return null;
        return Arrays.asList(getTupian().split("\\$;"));

    }
    private String tagname;

    private String typename;

    private String subtypename;

    public String getSubtypename() {
        return subtypename;
    }

    public void setSubtypename(String subtypename) {
        this.subtypename = subtypename;
    }

    private String shaccount;
    private String shname;


    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getShaccount() {
        return shaccount;
    }

    public void setShaccount(String shaccount) {
        this.shaccount = shaccount;
    }

    public String getShname() {
        return shname;
    }

    public void setShname(String shname) {
        this.shname = shname;
    }
}
