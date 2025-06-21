package com.daowen.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daowen.service.SpcommentService;
import com.daowen.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpcommentController {


	@Autowired
	private SpcommentService spcommentSrv=null;
	@Autowired
	private  HttpServletRequest request;


	@ResponseBody
	@PostMapping("/admin/spcomment/list")
	public JsonResult  spcomment(){
		String id=request.getParameter("spid");
		if(id==null)
			return JsonResult.error(-1,"参数异常");
		String sql=" select c.* ,h.accountname ,h.name ,h.touxiang as photo from spcomment c ,huiyuan h where h.id=c.appraiserid and c.spid="+id;
		List<HashMap<String,Object>> listMap=spcommentSrv.queryToMap(sql);
		return JsonResult.success(1,"获取评论信息",listMap);
	}


}
