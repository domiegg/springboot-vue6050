package com.daowen.service;
import com.daowen.entity.Coupon;
import com.daowen.vo.CouponVo;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public   class  CouponService extends  SimpleBizservice<CouponMapper>{

      @Autowired
      private  CouponMapper  couponMapper;


      public List<Coupon> getEntityPlus(Map map){
            return couponMapper.getEntityPlus(map);
      }
      public  Coupon loadPlus(Map map){
            return couponMapper.loadPlus(map);
      }

      public  Coupon   loadPlus(int id){
            HashMap map = new HashMap();
            map.put("id",id);
            return this.loadPlus(map);
      }



     
}