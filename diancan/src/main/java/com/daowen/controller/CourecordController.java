package com.daowen.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
import com.daowen.vo.CouponVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.ResponseBody;

//##{{import}}
@Controller
public class CourecordController extends SimpleController {

    @Autowired
    private CourecordService courecordSrv = null;

    //
    @ResponseBody
    @PostMapping("/admin/courecord/save")
    public JsonResult save() {

		String hyid=request.getParameter("hyid");
		String couponid=request.getParameter("couponid");
		String sql= MessageFormat.format(" where hyid={0} and couponid={1} ",hyid,couponid);
		if(hyid==null||couponid==null)
			JsonResult.error(-1,"参数异常");
		boolean exist=courecordSrv.isExist(sql);
		if(exist)
			return JsonResult.error(-2,"已经领过此卷");
	    Courecord	courecord=new Courecord();
		courecord.setHyid(Integer.parseInt(hyid));
		courecord.setCouponid(Integer.parseInt(couponid));
		courecord.setCreatetime(new Date());
		courecord.setState(1);
		courecordSrv.save(courecord);
		return JsonResult.success(1,"领劵成功");
    }


    @ResponseBody
    @PostMapping("/admin/courecord/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        courecordSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping({"/admin/courecord/load", "/admin/courecord/info"})
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        CouponVo courecord = courecordSrv.loadPlus(new Integer(id));
        if (courecord == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", courecord);

    }



    @ResponseBody
    @PostMapping("/admin/courecord/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String hyid = request.getParameter("hyid");
        String totalfee = request.getParameter("totalfee");
        String state = request.getParameter("state");
        if (hyid != null)
            map.put("hyid", hyid);
        if(totalfee!=null)
            map.put("totalfee",totalfee);
        if(state!=null)
            map.put("state",state);

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
            List<CouponVo> listCourecord = courecordSrv.getEntityPlus(map);
            PageInfo<CouponVo> pageInfo = new PageInfo<>(listCourecord);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", courecordSrv.getEntityPlus(map));


    }


    //##{{methods}}

}
