package com.daowen.mapper;

import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

/*
 *  售后
 **/
@Repository
public interface ShouhouMapper extends SimpleMapper<Shouhou> {

    List<Shouhou> getEntityPlus(HashMap map);

    Shouhou loadPlus(HashMap map);

}