package com.daowen.service;
import com.daowen.mapper.SpcommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;
@Service
public   class  SpcommentService extends  SimpleBizservice<SpcommentMapper>{

      @Autowired
      private  SpcommentMapper  spcommentMapper;


     
}