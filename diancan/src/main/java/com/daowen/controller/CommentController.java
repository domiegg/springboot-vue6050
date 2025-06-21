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

import com.daowen.entity.Agreerecord;

import com.daowen.service.AgreerecordService;
import com.daowen.service.SpcommentService;
import com.daowen.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daowen.entity.Comment;
import com.daowen.service.CommentService;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController extends SimpleController {

	@Autowired
	private CommentService commentSrv=null;
	@Autowired
	private SpcommentService spcommentSrv=null;
	@Autowired
	private AgreerecordService arSrv=null;



	@PostMapping("/admin/comment/list")
	public JsonResult list(){
		String belongid=request.getParameter("belongid");
		String xtype=request.getParameter("xtype");
		HashMap map=new HashMap();
		if(belongid!=null)
			map.put("belongid",belongid);
		if(xtype!=null)
			map.put("xtype",xtype);
		List<Comment> listMap=commentSrv.getEntityPlus(map);
		return JsonResult.success(1,"获取评论信息",listMap);
	}



	@PostMapping("/admin/comment/delete")
	public JsonResult delete() {
		String id = request.getParameter("id");
		if (id == null)
			return JsonResult.error(-1,"参数异常");
		String where = " where id="+id;
		commentSrv.delete(where);
		return JsonResult.success(1,"删除成功");
	}


	@PostMapping("/admin/comment/save")
	public JsonResult save() {


		String hyid = request.getParameter("hyid");
		String cdata = request.getParameter("cdata");
		String xtype = request.getParameter("xtype");
		String belongid = request.getParameter("belongid");
		String istopic=request.getParameter("istopic");
		String topicid=request.getParameter("topicid");
		Comment comment = new Comment();
		comment.setCreatetime(new Date());
		comment.setCdata(cdata == null ? "" : cdata);
		comment.setXtype(xtype == null ? "" : xtype);
		comment.setBelongid(belongid == null ? "" : belongid);
		if(istopic!=null)
			comment.setTopicid(new Integer(topicid));
		else
			comment.setTopicid(0);
		if(topicid!=null)
			comment.setIstopic(new Integer(istopic));
		else
			comment.setTopicid(0);
		comment.setHyid(hyid==null?0:new Integer(hyid));
		commentSrv.save(comment);
		return JsonResult.success(1,"评论成功");

	}



	@RequestMapping(value="/admin/comment/agree")
	public JsonResult agree(HttpServletRequest request, HttpServletResponse response) {
		String targetid = request.getParameter("targetid");
		String commentator = request.getParameter("commentator");
		String xtype = request.getParameter("xtype");
		if (targetid == null || commentator == null) {
			return JsonResult.error(-1, "参数异常");

		}

		Boolean hasExist = arSrv.isExist(MessageFormat.format(
				" where targetid={0}  and typename=''comment'' and commentator=''{1}'' ", targetid, commentator));
		if (hasExist) {
			return JsonResult.error(-2, "你已经操作过");

		}

		Comment comment = commentSrv.load("where id=" + targetid);
		if (comment == null) {
			return  JsonResult.error(-3, "数据异常");
		}
		Agreerecord ar = new Agreerecord();
		ar.setCommentator(commentator);
		ar.setTargetid(targetid);
		ar.setTypename("comment");
		arSrv.save(ar);
		comment.setAgreecount(comment.getAgreecount() + 1);
		commentSrv.update(comment);
		JsonResult jsonResult =JsonResult.success(1,"你赞了他");

		return  JsonResult.success(1,"赞完成");

	}


}
