package com.daowen.controller;

import java.text.SimpleDateFormat;
import java.util.*;

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
public class StagController extends SimpleController {

    @Autowired
    private StagService stagSrv = null;


    //
    @PostMapping("/admin/stag/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        SimpleDateFormat sdfstag = new SimpleDateFormat("yyyy-MM-dd");
        Stag stag = new Stag();


        stag.setName(name == null ? "" : name);


        //end forEach

        Boolean validateresult = stagSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        stagSrv.save(stag);
        return JsonResult.success(1, "成功", stag);
    }

    @PostMapping("/admin/stag/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Stag stag = stagSrv.load("where id=" + id);
        if (stag == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        SimpleDateFormat sdfstag = new SimpleDateFormat("yyyy-MM-dd");


        stag.setName(name == null ? "" : name);


        stagSrv.update(stag);
        return JsonResult.success(1, "成功", stag);

    }

    @PostMapping("/admin/stag/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        stagSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/stag/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Stag stag = stagSrv.loadPlus(new Integer(id));
        if (stag == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", stag);

    }

    @PostMapping("/admin/stag/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged=request.getParameter("ispaged");
        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);

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

        if(!"-1".equals(ispaged)) {
            PageHelper.startPage(pageindex, pagesize);
            List<Stag> listStag = stagSrv.getEntityPlus(map);
            PageInfo<Stag> pageInfo = new PageInfo<Stag>(listStag);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1,"获取成功",stagSrv.getEntityPlus(map));

    }



    //##{{methods}}


}
