package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  BrowserecService extends  SimpleBizservice<BrowserecMapper>{

      @Autowired
      private  BrowserecMapper  browserecMapper;



          public  List<Browserec>   getEntityPlus(HashMap map){
               return  browserecMapper.getEntityPlus(map);
          }
          
          public  Browserec   loadPlus(HashMap map){
              return browserecMapper.loadPlus(map);
          }
          
           public  Browserec   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}