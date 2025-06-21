package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  JiaodiantuService extends  SimpleBizservice<JiaodiantuMapper>{

      @Autowired
      private  JiaodiantuMapper  jiaodiantuMapper;



          public  List<Jiaodiantu>   getEntityPlus(HashMap map){
               return  jiaodiantuMapper.getEntityPlus(map);
          }
          
          public  Jiaodiantu   loadPlus(HashMap map){
              return jiaodiantuMapper.loadPlus(map);
          }
          
           public  Jiaodiantu   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}