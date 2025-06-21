package com.daowen.mapper;

import com.daowen.entity.Sitenav;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SitenavMapper extends SimpleMapper<Sitenav>{

    List<Sitenav> getEntityPlus(Map map);

    Sitenav  loadPlus(Map map);

}
