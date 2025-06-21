package com.daowen.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.daowen.entity.Hytype;
import com.daowen.service.HytypeService;
import com.daowen.util.JsonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.RestController;

//##{{import}}
@RestController
public class HytypeController extends SimpleController {

    @Autowired
    private HytypeService hytypeSrv = null;


    //
    @PostMapping("/admin/hytype/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        String discount = request.getParameter("discount");

        SimpleDateFormat sdfhytype = new SimpleDateFormat("yyyy-MM-dd");
        Hytype hytype = new Hytype();


        hytype.setName(name == null ? "" : name);


        hytype.setDiscount(discount == null ? 0 : new Double(discount));


        //end forEach

        Boolean validateresult = hytypeSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        hytypeSrv.save(hytype);
        return JsonResult.success(1, "成功", hytype);
    }

    @PostMapping("/admin/hytype/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Hytype hytype = hytypeSrv.load("where id=" + id);
        if (hytype == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        String discount = request.getParameter("discount");

        SimpleDateFormat sdfhytype = new SimpleDateFormat("yyyy-MM-dd");


        hytype.setName(name == null ? "" : name);


        hytype.setDiscount(discount == null ? 0 : new Double(discount));


        hytypeSrv.update(hytype);
        return JsonResult.success(1, "成功", hytype);

    }

    @PostMapping("/admin/hytype/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        hytypeSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/hytype/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Hytype hytype = hytypeSrv.loadPlus(new Integer(id));
        if (hytype == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", hytype);

    }

    @PostMapping("/admin/hytype/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();

        String name = request.getParameter("name");
        String ispaged=request.getParameter("ispaged");
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
        if(!"-1".equals(ispaged))
            PageHelper.startPage(pageindex, pagesize);
        List<Hytype> listHytype = hytypeSrv.getEntityPlus(map);
        if(!"-1".equals(ispaged)) {
            PageInfo<Hytype> pageInfo = new PageInfo<Hytype>(listHytype);

            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1,"成功",listHytype);

    }

    //##{{methods}}


}
