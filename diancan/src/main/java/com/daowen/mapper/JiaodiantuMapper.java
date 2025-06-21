package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  轮播图
**/
@Repository
public interface JiaodiantuMapper  extends SimpleMapper<Jiaodiantu> {

          List<Jiaodiantu>   getEntityPlus(HashMap map);
          
           Jiaodiantu   loadPlus(HashMap map);

}