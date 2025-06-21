package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  SubtypeService extends  SimpleBizservice<SubtypeMapper>{

      @Autowired
      private  SubtypeMapper  subtypeMapper;



          public  List<Subtype>   getEntityPlus(HashMap map){
               return  subtypeMapper.getEntityPlus(map);
          }
          
          public  Subtype   loadPlus(HashMap map){
              return subtypeMapper.loadPlus(map);
          }
          
           public  Subtype   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}