package com.daowen.mapper;

import com.daowen.entity.Comment;
import com.daowen.ssm.simplecrud.SimpleMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommentMapper extends SimpleMapper<Comment> {

	  List<Comment> getEntityPlus(Map map);

	  Comment loadPlus(Map map);
}
