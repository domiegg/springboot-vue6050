package com.daowen.controller;

import com.daowen.entity.Shoucang;
import com.daowen.service.ShoucangService;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.util.JsonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ShoucangController extends SimpleController {

	@Autowired
	private ShoucangService scSrv=null;

	@PostMapping("/admin/shoucang/delete")
	public JsonResult delete() {
		String[] ids = request.getParameterValues("ids");
		if (ids == null)
			return JsonResult.error(-1,"参数异常");
		String spliter = ",";
		String sql = " where id in(" + join(spliter, ids)+ ")";
		scSrv.delete(sql);
		return JsonResult.success(1,"成功");

	}

	@RequestMapping("/admin/shoucang/save")
	public JsonResult save() {
		// 验证错误url
		String targetid = request.getParameter("targetid");
		String targetname = request.getParameter("targetname");
		String tupian = request.getParameter("tupian");
		String hyid = request.getParameter("hyid");
		String xtype = request.getParameter("xtype");
		String href = request.getParameter("href");
		Shoucang shoucang = new Shoucang();
		shoucang.setTargetid(targetid == null ? 0 : new Integer(targetid));
		shoucang.setTargetname(targetname == null ? "" : targetname);
		shoucang.setTupian(tupian == null ? "" : tupian);
		shoucang.setHyid(hyid == null ? 0 : Integer.parseInt(hyid));
		shoucang.setSctime(new Date());
		shoucang.setXtype(xtype);
		shoucang.setHref(href==null?"":href);
		// 产生验证
		Boolean validateresult = scSrv.isExist(MessageFormat.format("where targetid=''{0}'' and hyid=''{1}'' and xtype=''{2}'' ", targetid, hyid,xtype));
		if (validateresult) {
			return JsonResult.error(-1,"你已经收藏了");
		}
		scSrv.save(shoucang);
		return JsonResult.success(1,"成功收藏");

	}


	@PostMapping("/admin/shoucang/list")
	public JsonResult list() {
		String filter = "where 1=1 ";
		String hyid = request.getParameter("hyid");
		//
		if (hyid != null)
			filter += "  and hyid=" + hyid;
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
		List<Shoucang> listShoucang =scSrv.getEntity(filter);
		return JsonResult.success(1,"收藏",new PageInfo<>(listShoucang));

	}


}
