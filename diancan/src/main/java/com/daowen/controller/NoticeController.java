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
public class NoticeController extends SimpleController {

    @Autowired
    private NoticeService noticeSrv = null;


    //
    @PostMapping("/admin/notice/save")
    public JsonResult save() {

        String title = request.getParameter("title");

        String pubren = request.getParameter("pubren");

        String pubtime = request.getParameter("pubtime");

        String clickcount = request.getParameter("clickcount");

        String dcontent = request.getParameter("dcontent");

        SimpleDateFormat sdfnotice = new SimpleDateFormat("yyyy-MM-dd");
        Notice notice = new Notice();


        notice.setTitle(title == null ? "" : title);


        notice.setPubren(pubren == null ? "" : pubren);


        if (pubtime != null) {
            try {
                notice.setPubtime(sdfnotice.parse(pubtime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            notice.setPubtime(new Date());
        }


        notice.setClickcount(clickcount == null ? 0 : new Integer(clickcount));


        notice.setDcontent(dcontent == null ? "" : dcontent);


        //end forEach

        Boolean validateresult = noticeSrv.isExist("  where  title='" + title + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        noticeSrv.save(notice);
        return JsonResult.success(1, "成功", notice);
    }

    @PostMapping("/admin/notice/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Notice notice = noticeSrv.load("where id=" + id);
        if (notice == null)
            return JsonResult.error(-2, "非法数据");

        String title = request.getParameter("title");

        String pubren = request.getParameter("pubren");

        String pubtime = request.getParameter("pubtime");

        String clickcount = request.getParameter("clickcount");

        String dcontent = request.getParameter("dcontent");

        SimpleDateFormat sdfnotice = new SimpleDateFormat("yyyy-MM-dd");


        notice.setTitle(title == null ? "" : title);


        notice.setPubren(pubren == null ? "" : pubren);


        if (pubtime != null) {
            try {
                notice.setPubtime(sdfnotice.parse(pubtime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            notice.setPubtime(new Date());
        }


        notice.setClickcount(clickcount == null ? 0 : new Integer(clickcount));


        notice.setDcontent(dcontent == null ? "" : dcontent);


        noticeSrv.update(notice);
        return JsonResult.success(1, "成功", notice);

    }

    @PostMapping("/admin/notice/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        noticeSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/notice/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Notice notice = noticeSrv.loadPlus(new Integer(id));
        if (notice == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", notice);

    }

    @PostMapping("/admin/notice/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged=request.getParameter("ispaged");

        String title = request.getParameter("title");
        if (title != null)
            map.put("title", title);

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
            List<Notice> listNotice = noticeSrv.getEntityPlus(map);

            PageInfo<Notice> pageInfo = new PageInfo<Notice>(listNotice);

            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1,"成功",noticeSrv.getEntityPlus(map));

    }


    //##{{methods}}


}
