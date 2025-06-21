package com.daowen.controller;

import com.daowen.entity.Pagesetting;
import com.daowen.entity.Psitem;
import com.daowen.service.LanmuService;
import com.daowen.service.PagesettingService;
import com.daowen.service.PsitemService;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.util.JsonResult;
import com.daowen.vo.PageSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

@RestController
public class PsitemController extends SimpleController {

    @Autowired
    private PagesettingService pagesettingSrv = null;
    @Autowired
    private LanmuService  lanmuSrv=null;
    @Autowired
    private PsitemService psitemSrv=null;
    @PostMapping("/admin/psitem/list")
    public JsonResult list(){

        //页面编号
        String pageid=request.getParameter("pageid");
        if(pageid==null||pageid=="")
            return JsonResult.error(-1,"pageid不能为空");
        Pagesetting pagesetting= pagesettingSrv.load("where id="+pageid);
        if(pagesetting==null)
            return JsonResult.error(-2,"无效的数据");
        PageSettingVo pageColumn = pagesettingSrv.getPageColumn(Integer.parseInt(pageid));
        if(pageColumn==null)
            return JsonResult.error(-3,"获取页面栏目信息出现异常");
        return  JsonResult.success(1,"获取页面栏目信息",pageColumn);

    }


    @PostMapping("/admin/psitem/delete")
    public JsonResult delete(){

        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1,"参数异常");
        String spliter = ",";
        String where = " where id in(" + join(spliter, ids)+ ")";

        psitemSrv.delete(where);
        return JsonResult.success(1,"删除成功");

    }


    @PostMapping("/admin/psitem/update")
    public JsonResult update(){
        String pageid=request.getParameter("pageid");
        String lmid=request.getParameter("lmid");
        Psitem psitem= psitemSrv.load(MessageFormat.format("where pageid={0} and lmid={1}",pageid,lmid));
        if(psitem!=null){

            psitemSrv.update(psitem);
        }
        return JsonResult.success(1,"修改成功");


    }

    @PostMapping("/admin/psitem/save")
    public JsonResult save(){

        String pageid=request.getParameter("pageid");
        String lmid=request.getParameter("lmid");
        Psitem psitem=new Psitem();
        psitem.setLmid(lmid==null?0:new Integer(lmid));
        psitem.setPageid(pageid==null?0:new Integer(pageid));

        Boolean has= psitemSrv.isExist(MessageFormat.format("where pageid={0} and lmid={1}",pageid,lmid));
        if(has)
            return JsonResult.error(-1,"已经添加了该栏目");
        psitemSrv.save(psitem);
        return JsonResult.success(1,"添加成功");

    }

    @RequestMapping("/admin/psitem/load")
    public JsonResult load(){

        String lmname=request.getParameter("lmname");
        String pageid = request.getParameter("pageid");

        if(pageid==null||pageid=="")
            return JsonResult.error(-1,"pageid不能为空");
        Pagesetting pagesetting= pagesettingSrv.load("where id="+pageid);
        if(pagesetting==null)
            return JsonResult.error(-2,"非法的数据");

        HashMap map = new HashMap();
        map.put("pageid",pageid);
        if(lmname!=null)
            map.put("lmname",lmname);
        List<HashMap<String, Object>> listLanmu = pagesettingSrv.getColumnState(map);

        return JsonResult.success(1,"获取成功",listLanmu);

    }



}
