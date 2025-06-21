package com.daowen.service;

import com.daowen.entity.Sitenav;
import com.daowen.mapper.SitenavMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SitenavService extends SimpleBizservice<SitenavMapper> {

    @Autowired
    private SitenavMapper sitenavMapper;

    public List<Sitenav> getEntityPlus(Map map){
        return  sitenavMapper.getEntityPlus(map);
    }

    public  Sitenav   loadPlus(Map map ){
        return  sitenavMapper.loadPlus(map);
    }

    public  Sitenav   loadPlus(int id ){
        HashMap map=new HashMap();
        map.put("id",id);
        return  sitenavMapper.loadPlus(map);
    }


}
