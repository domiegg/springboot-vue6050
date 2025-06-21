package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import com.daowen.vo.CouponVo;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  优惠券
**/
@Repository
public interface CouponMapper  extends SimpleMapper<Coupon> {


      List<Coupon> getEntityPlus(Map map);

      Coupon loadPlus(Map map);

      Coupon   loadPlus(int id);


}