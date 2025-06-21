package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import com.daowen.vo.CouponVo;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  领劵记录
**/
@Repository
public interface CourecordMapper  extends SimpleMapper<Courecord> {

          List<CouponVo>   getEntityPlus(HashMap map);

          CouponVo   loadPlus(HashMap map);

}