package com.daowen.service;

import com.daowen.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daowen.mapper.CommentMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService extends SimpleBizservice<CommentMapper>{

    @Autowired
    private CommentMapper commentMapper;
    public List<Comment> getEntityPlus(Map map){
        return commentMapper.getEntityPlus(map);
    }
    public Comment loadPlus(Map map){
        return commentMapper.loadPlus(map);
    }
    public Comment loadPlus(int id){
        HashMap map=new HashMap();
        map.put("id",id);
        return loadPlus(map);
    }
}
