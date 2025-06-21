package com.daowen.mapper;

import com.daowen.entity.Shangpin;
import com.daowen.ssm.simplecrud.SimpleMapper;
import com.daowen.vo.ShangpinVo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ShangpinMapper extends SimpleMapper<Shangpin> {


    List<ShangpinVo> getEntityPlus(HashMap map);

    ShangpinVo loadPlus(HashMap map);

    List<ShangpinVo> getRecomment(Map map);
	
}
