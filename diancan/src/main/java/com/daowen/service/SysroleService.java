package com.daowen.service;
import com.daowen.entity.Sysrole;
import com.daowen.mapper.SysroleMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;
import java.util.*;

@Service
public   class  SysroleService extends  SimpleBizservice<SysroleMapper>{

      @Autowired
      private  SysroleMapper  sysroleMapper;


       public List<Sysrole> getEntityPlus(Map map){
          return  sysroleMapper.getEntityPlus(map);
      }

      public  Sysrole   loadPlus(Map map ){
            return  sysroleMapper.loadPlus(map);
      }
      
     public  Sysrole   loadPlus(int id ){
             HashMap map=new HashMap();
             map.put("id",id);
            return  sysroleMapper.loadPlus(map);
      }

    public boolean deleteRole(int id){

        int count=this.delete("where id="+id);

        return  count>0?true:false;

    }


     
}