package com.daowen.vo;

import com.daowen.entity.Orderitem;
import com.daowen.entity.Spcomment;

import java.util.Arrays;
import java.util.List;

public class OrderItemDTO extends Orderitem {


    private String spname;
    private String tupian;

    private String shaccount;
    private String  shname;


    private Spcomment comment;




    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }


    public  List<String> getImages(){
        if(getTupian()==null)
            return null;
        return Arrays.asList(getTupian().split("\\$;"));

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

    public Spcomment getComment() {
        return comment;
    }

    public void setComment(Spcomment comment) {
        this.comment = comment;
    }
}