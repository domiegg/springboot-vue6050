package com.daowen.mapper;

import com.daowen.entity.Hytype;
import com.daowen.ssm.simplecrud.SimpleMapper;

import org.springframework.stereotype.Repository;

import java.util.*;

/*
 *  会员类型
 **/
@Repository
public interface HytypeMapper extends SimpleMapper<Hytype> {

    List<Hytype> getEntityPlus(HashMap map);

    Hytype loadPlus(HashMap map);

}