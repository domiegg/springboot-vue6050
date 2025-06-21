package com.daowen.service;
import com.daowen.entity.Lanmu;

import com.daowen.util.BeansUtil;
import com.daowen.vo.ILanmuAdapter;
import com.daowen.vo.ImageTextItem;
import com.daowen.vo.LanmuContentVo;
import com.daowen.vo.LanmuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daowen.mapper.LanmuMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LanmuService extends SimpleBizservice<LanmuMapper>{

    @Autowired
   private LanmuMapper mapper;

    public List<LanmuVo> getEntityPlus(Map map){
        return mapper.getEntityPlus(map);
    }

    public LanmuVo loadPlus(Map map){
        return mapper.loadPlus(map);
    }
    public LanmuVo loadPlus(int id){
        HashMap map=new HashMap();
        map.put("id",id);
        return mapper.loadPlus(map);
    }


    private ILanmuAdapter getLanmuimplementor(int code){
        Map<String, ILanmuAdapter> lanmuImplementors= BeansUtil.getBeanOfType(ILanmuAdapter.class);
        for (Map.Entry<String, ILanmuAdapter> entry : lanmuImplementors.entrySet()) {
            ILanmuAdapter lanmuAdapter=entry.getValue();
            if(lanmuAdapter!=null&&lanmuAdapter.getDefineCode()==code)
                return  lanmuAdapter;
        }
        return null;
    }
    /**
     * 栏目编号
     * @param lmId
     * @return  返回栏目内容
     */
    public LanmuContentVo<ImageTextItem> getContent(int lmId){

        Lanmu lanmu = load("where id=" + lmId);
        if(lanmu==null)
            return null;
        ILanmuAdapter lanmuAdapter=getLanmuimplementor(lanmu.getType());
        if(lanmuAdapter!=null)
            return lanmuAdapter.getLanmuContent(lanmu);
        return null;

    }

    public boolean  deleteLanmu(int id){
        Lanmu lanmu=load("where id="+id);
        if(lanmu==null)
            return false;
        int count=executeUpdate(" delete from lanmu where id="+id);
        executeUpdate(" delete from subtype where parentid="+id);
        int rcount=0;
        if(lanmu.getType()==1)
           rcount= executeUpdate(" delete from xinxi where lmid="+id);
        if(lanmu.getType()==2)
            rcount= executeUpdate(" delete form shangpin where typeid="+id);
        return count>0?true:false;

    }







}
