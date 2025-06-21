package com.daowen.service;


import com.daowen.entity.Hytype;
import com.daowen.mapper.HytypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;

@Service
public class HytypeService extends SimpleBizservice<HytypeMapper> {

    @Autowired
    private HytypeMapper hytypeMapper;


    public List<Hytype> getEntityPlus(HashMap map) {
        return hytypeMapper.getEntityPlus(map);
    }

    public Hytype loadPlus(HashMap map) {
        return hytypeMapper.loadPlus(map);
    }

    public Hytype loadPlus(int id) {
        HashMap map = new HashMap();
        map.put("id", id);
        return this.loadPlus(map);
    }

}