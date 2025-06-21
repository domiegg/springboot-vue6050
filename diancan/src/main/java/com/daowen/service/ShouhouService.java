package com.daowen.service;

import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;

@Service
public class ShouhouService extends SimpleBizservice<ShouhouMapper> {

    @Autowired
    private ShouhouMapper shouhouMapper;


    public List<Shouhou> getEntityPlus(HashMap map) {
        return shouhouMapper.getEntityPlus(map);
    }

    public Shouhou loadPlus(HashMap map) {
        return shouhouMapper.loadPlus(map);
    }

    public Shouhou loadPlus(int id) {
        HashMap map = new HashMap();
        map.put("id", id);
        return this.loadPlus(map);
    }

}