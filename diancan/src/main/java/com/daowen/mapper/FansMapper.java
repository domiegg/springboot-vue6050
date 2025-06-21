package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  粉丝
**/
@Repository
public interface FansMapper  extends SimpleMapper<Fans> {

          List<Fans>   getEntity();

}