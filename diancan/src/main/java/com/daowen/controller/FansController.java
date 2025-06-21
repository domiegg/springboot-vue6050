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
public class FansController extends SimpleController {

    @Autowired
    private FansService fansSrv = null;

    @Autowired
    private HuiyuanService huiyuanSrv=null;

    @Override
    @RequestMapping("/admin/fansmanager.do")
    public void mapping(HttpServletRequest request, HttpServletResponse response) {
        mappingMethod(request, response);
    }

    @ResponseBody
    @PostMapping("/admin/fans/save")
    public JsonResult save() {
        String targetid = request.getParameter("targetid");
        String actionid = request.getParameter("actionid");
        String createtime = request.getParameter("createtime");
        SimpleDateFormat sdffans = new SimpleDateFormat("yyyy-MM-dd");
        Fans fans = new Fans();
        fans.setTargetid(targetid == null ? 0 : Integer.parseInt(targetid));
        fans.setActionid(actionid == null ? 0 : Integer.parseInt(actionid));
        fans.setCreatetime(new Date());
        String filter= MessageFormat.format(" where targetid={0} and  actionid={1} ",targetid,actionid);
        Boolean bExist = fansSrv.isExist(filter);
        if(bExist)
           return JsonResult.error(-1,"已经关注过,不需要再关注");
        if(fans.getTargetid()==fans.getActionid())
            return JsonResult.error(-2,"不能自己关注自己哦！");

        fansSrv.save(fans);
        huiyuanSrv.executeUpdate("update huiyuan set fanscount=fanscount+1 where id="+targetid);
        return  JsonResult.success(1,"成功关注");
    }


    @ResponseBody
    @PostMapping("/admin/fans/delete")
    public JsonResult delete() {
        String id=request.getParameter("id");
        if(id==null)
            return JsonResult.error(-1,"参数异常");

        fansSrv.delete("where id="+id);
        return JsonResult.success(1,"删除成功");
    }




}
