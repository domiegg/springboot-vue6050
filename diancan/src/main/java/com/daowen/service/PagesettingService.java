package com.daowen.service;

import com.daowen.mapper.PagesettingMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;
import com.daowen.vo.LanmuContentVo;
import com.daowen.vo.PageContentVo;
import com.daowen.vo.PageSettingVo;
import com.daowen.vo.PsItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PagesettingService extends SimpleBizservice<PagesettingMapper>{


    /**
     * 获取页面栏目
     * @param pageId
     * @return
     */
    public PageSettingVo getPageColumn(int pageId){
       return getMapper().getPageColumn(pageId);
    }

    /**
     * 获取页面内容
     * @param pageId
     * @return
     */
    public PageContentVo getPageContent(int pageId){

        PageSettingVo pageSettingVo=getPageColumn(pageId);
        if(pageSettingVo==null||pageSettingVo.getPsItemVos()==null)
            return null;
        PageContentVo pageContentVo=new PageContentVo();
        pageContentVo.setPageId(pageId);
        pageContentVo.setPageName(pageSettingVo.getPageName());

        List<LanmuContentVo> list=new ArrayList<>();
        if(pageSettingVo!=null&&pageSettingVo.getPsItemVos()!=null) {

            for (PsItemVo psItemVo : pageSettingVo.getPsItemVos()) {
                LanmuContentVo content = lanmuSrv.getContent(psItemVo.getLmId());
                if (content != null) {
                    list.add(content);
                }
            }
        }
        pageContentVo.setListLanmuContent(list);
        return  pageContentVo;
    }

   public  List<HashMap<String,Object>> getColumnState(Map map){
        return pagesettingMapper.getColumnState(map);
    }
     @Autowired
     private LanmuService lanmuSrv=null;
    @Autowired
    private PagesettingMapper pagesettingMapper;

}
