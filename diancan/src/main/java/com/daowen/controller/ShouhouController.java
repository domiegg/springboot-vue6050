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
public class  ShouhouController extends SimpleController {

    @Autowired
    private ShouhouService shouhouSrv = null;

    @Autowired
    private OrderitemService oiSrv=null;

    @Autowired
    private ShorderService  shorderSrv=null;
    @Autowired
    private HuiyuanService huiyuanSrv=null;

    //
    @PostMapping("/admin/shouhou/save")
    public JsonResult save() {

        String orderno = request.getParameter("orderno");
        String spname = request.getParameter("spname");
        String spid = request.getParameter("spid");
        String count = request.getParameter("count");
        String price = request.getParameter("price");
        String tupian = request.getParameter("tupian");
        String reason = request.getParameter("reason");
        String tdes = request.getParameter("tdes");
        String oid = request.getParameter("oid");
        String oiid = request.getParameter("oiid");
        SimpleDateFormat sdfshouhou = new SimpleDateFormat("yyyy-MM-dd");
        Shouhou shouhou = new Shouhou();
        shouhou.setOrderno(orderno == null ? "" : orderno);
        shouhou.setSpname(spname == null ? "" : spname);
        shouhou.setSpid(spid == null ? 0 : new Integer(spid));
        shouhou.setCount(count == null ? 0 : new Integer(count));
        shouhou.setPrice(price == null ? 0 : new Double(price));
        shouhou.setTotalfee(shouhou.getPrice()*shouhou.getCount());
        shouhou.setTupian(tupian == null ? "" : tupian);
        shouhou.setState(1);
        shouhou.setCreatetime(new Date());
        shouhou.setReason(reason == null ? "" : reason);
        shouhou.setTdes(tdes == null ? "" : tdes);
        shouhou.setOid(oid == null ? 0 : new Integer(oid));
        shouhou.setOiid(oiid == null ? 0 : new Integer(oiid));
        Shouhou sh = shouhouSrv.save(shouhou);
        if(sh.getId()>0){
            Orderitem oi=oiSrv.load("where id="+sh.getOiid());
            if(oi!=null){
                //已申请售后处理
                oi.setState(6);
                oiSrv.update(oi);
                return JsonResult.success(1, "成功", shouhou);
            }
        }
        return JsonResult.success(-1, "失败");
    }


    @PostMapping("/admin/shouhou/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        shouhouSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/shouhou/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Shouhou shouhou = shouhouSrv.loadPlus(new Integer(id));
        if (shouhou == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", shouhou);

    }

    @RequestMapping("/admin/shouhou/info")
    public JsonResult info() {
        String id = request.getParameter("id");
        String oiid=request.getParameter("oiid");

        if (id == null&&oiid==null)
            return JsonResult.error(-1, "参数异常");
        if(oiid!=null) {
            HashMap map = new HashMap();
            map.put("oiid", oiid);
            List<Shouhou> listShouhou = shouhouSrv.getEntityPlus(map);
            if(listShouhou!=null&&listShouhou.size()>0)
                return JsonResult.success(1,"成功",listShouhou.get(0));
            else
                return JsonResult.error(-2,"数据异常");
        }
        Shouhou shouhou = shouhouSrv.loadPlus(new Integer(id));
        if (shouhou == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", shouhou);

    }

    @PostMapping("/admin/shouhou/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String orderno = request.getParameter("orderno");
        if (orderno != null)
            map.put("orderno", orderno);

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
            List<Shouhou> listShouhou = shouhouSrv.getEntityPlus(map);
            PageInfo<Shouhou> pageInfo = new PageInfo<Shouhou>(listShouhou);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", shouhouSrv.getEntityPlus(map));


    }

    @PostMapping("/admin/shouhou/shenpi")
    public JsonResult  shenpi(){

        String id=request.getParameter("id");

        String  state =request.getParameter("state");
        String  reply=request.getParameter("reply");
        String  shenpiren=request.getParameter("shenpiren");
        int stateCode=2;
        //验证错误url
        String errorurl=request.getParameter("errorurl");
        if(id==null)
            return JsonResult.error(-1,"id不能为空");;
        Shouhou  shouhou=shouhouSrv.load(" where id="+ id);

        if(shouhou==null)
            return JsonResult.error(-1,"数据非法");;
        if(state!=null)
            stateCode=Integer.parseInt(state);
        shouhou.setState(stateCode);
        shouhouSrv.update(shouhou);

        if(shouhou.getState()==2) {
            Shorder order = shorderSrv.loadPlus(shouhou.getOid());
            if (order != null && order.getPurchaser() > 0)
                huiyuanSrv.deposit(order.getPurchaser(), order.getTotalfee());
        }
        return JsonResult.success(1,"审核成功");


    }

    //##{{methods}}


}
