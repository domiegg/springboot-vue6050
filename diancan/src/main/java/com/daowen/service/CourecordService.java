package com.daowen.service;
import com.daowen.entity.*;
import com.daowen.vo.CouponVo;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  CourecordService extends  SimpleBizservice<CourecordMapper>{

      @Autowired
      private  CourecordMapper  courecordMapper;



          public  List<CouponVo>   getEntityPlus(HashMap map){
               return  courecordMapper.getEntityPlus(map);
          }
          
          public  CouponVo   loadPlus(HashMap map){
              return courecordMapper.loadPlus(map);
          }
          
           public  CouponVo   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}