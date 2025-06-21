package com.daowen.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.RestController;

//##{{import}}
@RestController
public class BrowserecController extends SimpleController {

    @Autowired
    private BrowserecService browserecSrv = null;


    //接收前端传来的浏览记录数据，保存到数据库中，并返回保存结果的JSON格式数据。
    @PostMapping("/admin/browserec/save")//指定了处理的HTTP POST请求的路径为
    public JsonResult save() {//回处理结果的JSON格式数据。

        String hyid = request.getParameter("hyid");

        String targetid = request.getParameter("targetid");



        SimpleDateFormat sdfbrowserec = new SimpleDateFormat("yyyy-MM-dd");
        Browserec browserec = new Browserec();


        browserec.setHyid(hyid == null ? 0 : new Integer(hyid));


        browserec.setTargetid(targetid == null ? 0 : new Integer(targetid));


        browserec.setType("yingpian");
        //end forEach

        Boolean validateresult = browserecSrv.isExist(MessageFormat.format("  where  hyid={0} and targetid={1} and type=''yingpian''  " ,hyid));
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        browserecSrv.save(browserec);
        return JsonResult.success(1, "成功", browserec);
    }


    @PostMapping("/admin/browserec/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        browserecSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }//接收前端传来的要删除的浏览记录的ID，然后根据ID删除相应的记录，并返回删除结果的JSON格式数据。

    @RequestMapping("/admin/browserec/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Browserec browserec = browserecSrv.loadPlus(new Integer(id));
        if (browserec == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", browserec);

    }//接收前端传来的要加载的浏览记录的ID，然后根据ID加载相应的记录，并返回加载结果的JSON格式数据。

    @RequestMapping("/admin/browserec/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Browserec browserec = browserecSrv.loadPlus(new Integer(id));
        if (browserec == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", browserec);

    }


    @PostMapping("/admin/browserec/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String hyid = request.getParameter("hyid");
        if (hyid != null)
            map.put("hyid", hyid);

        int pageindex = 1;
        int pagesize = 10;
        // 获取当前分页
        String currentpageindex = request.getParameter("currentpageindex");
        // 当前页面尺寸
        String currentpagesize = request.getParameter("pagesize");
        // 设置当前页
        if (currentpageindex != null)
            pageindex = new Integer(currentpageindex);
        // 设置当前页尺寸
        if (currentpagesize != null)
            pagesize = new Integer(currentpagesize);
        if (!"-1".equals(ispaged)) {
            PageHelper.startPage(pageindex, pagesize);
            List<Browserec> listBrowserec = browserecSrv.getEntityPlus(map);
            PageInfo<Browserec> pageInfo = new PageInfo<Browserec>(listBrowserec);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", browserecSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
