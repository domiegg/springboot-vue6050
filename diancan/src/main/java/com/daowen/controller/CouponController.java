package com.daowen.controller;

import java.io.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daowen.util.JsonResult;
import com.daowen.vo.CouponVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;
import org.springframework.web.bind.annotation.ResponseBody;

//##{{import}}
@Controller
public class CouponController extends SimpleController {

    @Autowired
    private CouponService couponSrv = null;


    @ResponseBody//保存优惠券信息
    @PostMapping("/admin/coupon/save")
    public JsonResult save() {



        String name = request.getParameter("name");


        String fee = request.getParameter("fee");


        String minreq = request.getParameter("minreq");


        String createtime = request.getParameter("createtime");


        String overdate = request.getParameter("overdate");

        SimpleDateFormat sdfcoupon = new SimpleDateFormat("yyyy-MM-dd");
        Coupon coupon = new Coupon();


        coupon.setName(name == null ? "" : name);


        coupon.setFee(fee == null ? 0 : new Integer(fee));


        coupon.setMinreq(minreq == null ? 0 : new Integer(minreq));


        if (createtime != null) {
            try {
                coupon.setCreatetime(sdfcoupon.parse(createtime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            coupon.setCreatetime(new Date());
        }


        if (overdate != null) {
            try {
                coupon.setOverdate(sdfcoupon.parse(overdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            coupon.setOverdate(new Date());
        }

        Boolean validateresult = couponSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1,"已存在的名称");

        couponSrv.save(coupon);
        return JsonResult.success(1,"成功");
    }

    @ResponseBody
    @PostMapping("/admin/coupon/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"id不能为空");
        Coupon coupon = couponSrv.load("where id=" + id);
        if (coupon == null)
            return JsonResult.error(-2,"非法数据");


        String name = request.getParameter("name");


        String fee = request.getParameter("fee");


        String minreq = request.getParameter("minreq");

        String overdate = request.getParameter("overdate");

        SimpleDateFormat sdfcoupon = new SimpleDateFormat("yyyy-MM-dd");


        coupon.setName(name == null ? "" : name);


        coupon.setFee(fee == null ? 0 : new Integer(fee));


        coupon.setMinreq(minreq == null ? 0 : new Integer(minreq));
        coupon.setCreatetime(new Date());

        if (overdate != null) {
            try {
                coupon.setOverdate(sdfcoupon.parse(overdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            coupon.setOverdate(new Date());
        }


        couponSrv.update(coupon);
         return JsonResult.success(1,"更新成功");
    }



    @ResponseBody
    @PostMapping("/admin/coupon/delete")
    public  JsonResult  delete(){
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1,"ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids)+ ")";
        couponSrv.delete(where);
        return  JsonResult.success(1,"不能为空");
    }
    @ResponseBody
    @RequestMapping({"/admin/coupon/load","/admin/coupon/info"})
    public  JsonResult  load(){
        String id=request.getParameter("id");

        if(id==null)
            return JsonResult.error(-1,"ID不能为空");
        Coupon  coupon=couponSrv.loadPlus(new Integer(id));
        if(coupon==null)
            return JsonResult.error(-2,"非法数据");
        return  JsonResult.success(1,"成功",coupon);

    }


    @ResponseBody
    @PostMapping("/admin/coupon/list")
    public  JsonResult  list(){

        HashMap<String,Object>  map=new HashMap<>();
        String ispaged=request.getParameter("ispaged");

        String name=request.getParameter("name");
        String hyid=request.getParameter("hyid");
        if(name!=null)
            map.put("name",name);
        if(hyid!=null)
            map.put("hyid",hyid);

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
            PageHelper.startPage(pageindex,pagesize);
            List<Coupon> listCoupon = couponSrv.getEntityPlus(map);
            PageInfo<Coupon> pageInfo=new PageInfo<>(listCoupon);
            return JsonResult.success(1,"成功",pageInfo);
        }
        return JsonResult.success(1,"获取成功", couponSrv.getEntityPlus(map));


    }
    //##{{methods}}


}
