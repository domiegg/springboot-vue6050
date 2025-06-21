package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  NoticeService extends  SimpleBizservice<NoticeMapper>{

      @Autowired
      private  NoticeMapper  noticeMapper;



          public  List<Notice>   getEntityPlus(HashMap map){
               return  noticeMapper.getEntityPlus(map);
          }
          
          public  Notice   loadPlus(HashMap map){
              return noticeMapper.loadPlus(map);
          }
          
           public  Notice   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}