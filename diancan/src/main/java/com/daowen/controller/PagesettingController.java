package com.daowen.controller;

import com.daowen.entity.Pagesetting;
import com.daowen.service.LanmuService;
import com.daowen.service.PagesettingService;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.util.JsonResult;
import com.daowen.vo.PageContentVo;
import com.daowen.vo.PageSettingVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;

/**************************
 *
 * 页面设置控制
 *
 */
@RestController
public class PagesettingController extends SimpleController {
    @Autowired
    private PagesettingService pagesettingSrv = null;

    @Autowired
    private LanmuService lanmuSrv = null;

    @PostMapping("/admin/pagesetting/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1,"编号不能为空");
        String where = " where id in(" + join(",", ids) + ")";

        pagesettingSrv.delete(where);
        return JsonResult.success(1,"删除成功");
    }

    @PostMapping("/admin/pagesetting/save")
    public JsonResult save() {

        //获取名称
        String name = request.getParameter("name");
        SimpleDateFormat sdfpagesetting = new SimpleDateFormat("yyyy-MM-dd");
        Pagesetting pagesetting = new Pagesetting();
        // 设置名称
        pagesetting.setName(name == null ? "" : name);
        //产生验证
        Boolean validateresult = pagesettingSrv.isExist("where name='" + name + "'");
        if (validateresult)
            return  JsonResult.error(-1,"已存在的名称");

        pagesettingSrv.save(pagesetting);
        return JsonResult.success(1,"新增成功");
    }


    @PostMapping("/admin/pagesetting/update")
    public JsonResult update() {
        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"ID不能为空");
        Pagesetting pagesetting = pagesettingSrv.load(new Integer(id));
        if (pagesetting == null)
            return JsonResult.error(-2,"非法的数据");
        //获取名称
        String name = request.getParameter("name");

         // 设置名称
        pagesetting.setName(name);
        pagesettingSrv.update(pagesetting);
        return JsonResult.success(1,"更新成功",pagesetting);
    }


    @PostMapping("/admin/pagesetting/load")
    public JsonResult load() {
        //
        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"ID不能为空");
        Pagesetting pagesetting = pagesettingSrv.load(new Integer(id));
        if (pagesetting == null)
            return JsonResult.error(-2,"非法的数据");
        return JsonResult.success(1,"更新成功",pagesetting);
    }


	@RequestMapping("/admin/pagesetting/column/{pageId}")
    public JsonResult getPageColumn(@PathVariable("pageId") int pageId){
        PageSettingVo psv=pagesettingSrv.getPageColumn(pageId);
        if(psv==null)
            return JsonResult.error(-1,"获取信息出现异常");
        return JsonResult.success(1,"获取成功",psv);
    }


    @RequestMapping("/admin/pagesetting/content")
    public JsonResult getPageContent(){
        String areaid=request.getParameter("areaid");
        PageContentVo pageContentVo=pagesettingSrv.getPageContent(new Integer(areaid));
        if(pageContentVo==null)
            return JsonResult.error(-1,"获取信息出现异常");
        return JsonResult.success(1,"获取成功",pageContentVo);
    }

    @PostMapping("/admin/pagesetting/list")
    public JsonResult list() {
        String filter = "where 1=1 ";

        String name = request.getParameter("name");
        if (name != null)
            filter += "  and name like '%" + name + "%'  ";
        //
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
        PageHelper.startPage(pageindex,pagesize);
        List<Pagesetting> listPagesetting = pagesettingSrv.getEntity(filter);
        return  JsonResult.success(1,"成功",new PageInfo<>(listPagesetting));
    }


}
