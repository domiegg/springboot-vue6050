package com.daowen.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daowen.util.JsonResult;
import com.daowen.vo.LeavewordVo;
import com.sun.org.glassfish.gmbal.ParameterNames;
import jdk.nashorn.internal.scripts.JS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;

//##{{import}}
@Controller
public class LeavewordController extends SimpleController {

    @Autowired
    private LeavewordService leavewordSrv = null;


    //
    @ResponseBody
    @PostMapping("/admin/leaveword/list")
    public JsonResult  list(){
        HashMap<String,Object> map=new HashMap<>();
        String hyid=request.getParameter("hyid");
        if(hyid!=null)
            map.put("hyid",hyid);
        map.put("order","order by id desc");
        List<LeavewordVo> listLeaveword=leavewordSrv.getEntityPlus(map);
        return JsonResult.success(1,"获取留言信息",listLeaveword);
    }

    @ResponseBody
    @PostMapping("/admin/leaveword/delete")
    public JsonResult  delete(){

        String []ids=request.getParameterValues("ids");
        if(ids==null)
            return JsonResult.error(-1,"参数异常");

        for(String id : ids){
            leavewordSrv.delete("where id="+id);
        }
        return JsonResult.success(1,"删除成功");
    }

    @ResponseBody
    @PostMapping("/admin/leaveword/save")//保存留言信息
    public JsonResult ajaxsave() {
        String dcontent = request.getParameter("dcontent");
        String hyid = request.getParameter("hyid");
        String replyren = request.getParameter("replyren");//留言内容、会员ID和回复人。
        SimpleDateFormat sdfleaveword = new SimpleDateFormat("yyyy-MM-dd");
        Leaveword leaveword = new Leaveword();
        leaveword.setDcontent(dcontent == null ? "" : dcontent);
        leaveword.setPubtime(new Date());
        leaveword.setState(1);
        leaveword.setHyid(hyid == null ? 0 : new Integer(hyid));
        leaveword.setReplytime(new Date());
        leaveword.setReplyren(replyren == null ? "" : replyren);
        leavewordSrv.save(leaveword);
        return JsonResult.success(1,"留言成功",leaveword);
    }

    @ResponseBody
    @PostMapping("/admin/leaveword/reply")
    public JsonResult reply() {
        String id = request.getParameter("id");
        String replyren = request.getParameter("replyren");
        if (id == null)
            return JsonResult.error(-1,"id不能为空");
        Leaveword leaveword = leavewordSrv.load("where id="+id);
        if (leaveword == null)
            return JsonResult.error(-2,"非法数据");
        String replycontent = request.getParameter("replycontent");
        leaveword.setReplycontent(replycontent);
        leaveword.setReplytime(new Date());
        leaveword.setReplyren(replyren);
        leaveword.setState(2);
        leavewordSrv.update(leaveword);
        return JsonResult.success(1,"成功");

    }

    //##{{methods}}


}
