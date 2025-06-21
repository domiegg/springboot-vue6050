package com.daowen.vo;

import com.daowen.entity.Lanmu;
import com.daowen.entity.Subtype;

import java.util.Iterator;
import java.util.List;

public class LanmuVo extends Lanmu {

    private List<Subtype> subtypes;



    public List<Subtype> getSubtypes() {
        if(subtypes!=null) {
            Iterator<Subtype> it = subtypes.iterator();
            while (it.hasNext()) {
                Subtype x = it.next();
                if (x.getId() == 0) {
                    it.remove();
                }
            }
        }
        return subtypes;
    }

    public void setSubtypes(List<Subtype> subtypes) {
        this.subtypes = subtypes;
    }




}
