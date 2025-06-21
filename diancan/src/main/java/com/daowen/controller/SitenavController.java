package com.daowen.controller;

import com.daowen.entity.Sitenav;
import com.daowen.service.SitenavService;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.util.JsonResult;
import com.daowen.webcontrol.PagerMetal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@RestController
public class SitenavController extends SimpleController {

	@Autowired
	private SitenavService sitenavSrv = null;

	@PostMapping("/admin/sitenav/delete")
	public JsonResult delete() {
		String[] ids = request.getParameterValues("ids");
		if (ids == null)
			return JsonResult.error(-1, "编号不能为空");
		String sql = " where id in(" + StringUtils.join(ids, ",") + ")";
		System.out.println("sql=" + sql);
		sitenavSrv.delete(sql);
		return JsonResult.success(1, "删除成功");
	}

	@PostMapping("/admin/sitenav/save")
	public JsonResult save() {

		String title = request.getParameter("title");
		String href = request.getParameter("href");
		String sindex = request.getParameter("sindex");
		SimpleDateFormat sdfsitenav = new SimpleDateFormat("yyyy-MM-dd");
		Sitenav sitenav = new Sitenav();
		sitenav.setTitle(title == null ? "" : title);
		sitenav.setHref(href == null ? "" : href);
		sitenav.setSindex(sindex == null ? 0 : new Integer(sindex));
		sitenavSrv.save(sitenav);
		return JsonResult.success(1, "新增成功");
	}

	@PostMapping("/admin/sitenav/update")
	public JsonResult update() {
		String forwardurl = request.getParameter("forwardurl");
		String id = request.getParameter("id");
		if (id == null)
			return JsonResult.error(-1, "编号不能为空");
		Sitenav sitenav = (Sitenav) sitenavSrv.load(new Integer(id));
		if (sitenav == null)
			return JsonResult.error(-2, "非法的数据");
		;
		String title = request.getParameter("title");
		String href = request.getParameter("href");
		String sindex = request.getParameter("sindex");
		SimpleDateFormat sdfsitenav = new SimpleDateFormat("yyyy-MM-dd");
		sitenav.setTitle(title);
		sitenav.setHref(href);
		sitenav.setSindex(sindex == null ? 0 : new Integer(sindex));
		sitenavSrv.update(sitenav);
		return JsonResult.success(1, "成功更新", sitenav);

	}

	@PostMapping("/admin/sitenav/load")
	public JsonResult load() {
		//
		String id = request.getParameter("id");
		if (id == null)
			return JsonResult.error(-1, "编号不能为空");
		Sitenav sitenav = sitenavSrv.load(new Integer(id));
		if (sitenav == null)
			return JsonResult.error(-2, "非法的数据");

		return JsonResult.success(1, "成功更新", sitenav);

	}

	@PostMapping("/admin/sitenav/list")
	public JsonResult list() {

		String title = request.getParameter("title");
		String ispaged = request.getParameter("ispaged");
		String order = request.getParameter("order");
		HashMap map = new HashMap();
		if (title != null)
			map.put("title", title);
		if(order!=null)
			map.put("order",order);

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
		if (!"-1".equals(ispaged)){
			PageHelper.startPage(pageindex, pagesize);
			List<Sitenav> listSitenav = sitenavSrv.getEntityPlus(map);
			PageInfo<Sitenav> pageInfo = new PageInfo<Sitenav>(listSitenav);
			return JsonResult.success(1, "获取成功", pageInfo);
		}
		return JsonResult.success(1, "获取成功", sitenavSrv.getEntityPlus(map));

	}

}