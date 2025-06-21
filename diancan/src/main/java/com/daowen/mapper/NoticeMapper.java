package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  公告
**/
@Repository
public interface NoticeMapper  extends SimpleMapper<Notice> {

          List<Notice>   getEntityPlus(HashMap map);
          
           Notice   loadPlus(HashMap map);

}