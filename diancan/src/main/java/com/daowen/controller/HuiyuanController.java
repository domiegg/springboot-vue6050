package com.daowen.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.daowen.entity.Chongzhirec;
import com.daowen.service.ChongzhirecService;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.daowen.entity.Huiyuan;
import com.daowen.service.HuiyuanService;


@Controller
public class HuiyuanController extends SimpleController {

	@Autowired
	private HuiyuanService huiyuanSrv=null;
	@Autowired
	private ChongzhirecService chongzhirecSrv=null;



	@ResponseBody
	@PostMapping("/admin/huiyuan/des")
	public JsonResult des() {
		//
		String id = request.getParameter("id");
		String des=request.getParameter("des");
		if (id == null)
			return JsonResult.error(-1, "参数不存在");

		Huiyuan huiyuan =null;
		if(id!=null&&id!=""){
			huiyuan=huiyuanSrv.loadPlus(new Integer(id));
			if (huiyuan == null)
				return JsonResult.error(-2, "不存在的账号");
		}
		huiyuan.setDes(des==null?"":des);
		huiyuanSrv.update(huiyuan);

		return JsonResult.success(1,"成功",huiyuan);

	}//根据会员账号的 id 更新会员的描述信息，并返回更新后的会员信息。
	@ResponseBody
	@PostMapping("/admin/huiyuan/chongzhi")
	public JsonResult chongzhi() {
		String amount = request.getParameter("amount");

		String hyid = request.getParameter("hyid");
		if (hyid == null || hyid == "")
			return JsonResult.error(-1,"账户编号不能为空");
		Huiyuan huiyuan = huiyuanSrv.load(new Integer(hyid));
		if (huiyuan == null)
			return JsonResult.error(-1,"账户不合法");

		huiyuan.setYue(huiyuan.getYue() + Double.valueOf(amount));
		huiyuanSrv.update(huiyuan);
		request.getSession().setAttribute("huiyuan", huiyuan);

		return JsonResult.success(1,"充值成功");
	}

	@RequestMapping("/admin/huiyuanmanager.do")
	public void mapping(HttpServletRequest request, HttpServletResponse response) {
		this.mappingMethod(request, response);
	}

	@ResponseBody
	@PostMapping("/admin/huiyuan/forgetpw")
	public JsonResult forgetpw() {
		String accountname = request.getParameter("accountname");
		Huiyuan h = huiyuanSrv.load("where accountname='" + accountname + "'");
		if (h == null)
			return JsonResult.error(-1, "不存在的账号");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("url", "/forgetpwnext.jsp?id=" + h.getId());
		return JsonResult.success(1, "", jsonObject);
	}


	@ResponseBody
	@PostMapping("/admin/huiyuan/sendpwemail")
	public JsonResult sendpwemail() {
		String id = request.getParameter("id");
		Huiyuan h = huiyuanSrv.load("where id=" + id);
		if (h == null)
			return JsonResult.error(-1, "不存在的账号");
		MimeMessageDescription mmd = new MimeMessageDescription();
		mmd.setReceAccount(h.getEmail());
		mmd.setReceAccountRemark(h.getName());
		mmd.setSubject("忘记密码-密码重置邮件");
		mmd.setContent(MessageFormat.format("亲忘记密码<a href=\"http://localhost:8080{0}/e/resetpw.jsp?id={1,number,#}\">重置密码</a>", request.getContextPath(), h.getId()));
		boolean res = MailUtil.send(mmd);
		if (res)
			return JsonResult.success(1, "");
		else
			return JsonResult.error(-2, "发送失败,请检查邮箱是否正常");
	}

	public void resetpw() {
		String repassword1 = request.getParameter("repassword1");
		String repassword2 = request.getParameter("repassword2");
		String forwardurl = request.getParameter("forwardurl");
		String errorurl = request.getParameter("errorurl");
		String id = request.getParameter("id");
		if (id == null || id == "")
			return;
		Huiyuan huiyuan = huiyuanSrv.load(new Integer(id));
		if (huiyuan == null) {
			request.setAttribute("errormsg", "<label class='error'>账户不成立</label>");
			forward(errorurl);
			return;
		}
		huiyuan.setPassword(repassword1);
		huiyuanSrv.update(huiyuan);
		request.getSession().setAttribute("huiyuan", huiyuan);
		forward(forwardurl);


	}



	@ResponseBody//返回的是HTTP响应体，而不是视图。
	@PostMapping("/admin/huiyuan/modifypaypw")//这个方法只处理POST类型的HTTP请求，并且它的URL路径是"/admin/huiyuan/forgetpw"。

	public JsonResult modifyPaypw() {

		String paypwd = request.getParameter("paypwd");
		String errorurl = request.getParameter("errorurl");
		String forwardurl = request.getParameter("forwardurl");
		String repassword1 = request.getParameter("repassword1");
		String id = request.getParameter("id");
		if (id == null || id == "")
			return JsonResult.error(-1, "编号不能为空");
		Huiyuan huiyuan = huiyuanSrv.load(new Integer(id));

		if (huiyuan == null)
			return JsonResult.error(-2, "不存在的账号信息");
		if (!huiyuan.getPaypwd().equals(paypwd))
			return JsonResult.error(-3, "原始支付密码不正确");
		huiyuan.setPaypwd(repassword1);
		huiyuanSrv.update(huiyuan);
		return JsonResult.success(1, "更新成功");
	}//主要功能是处理忘记密码的请求。根据提供的账号名查找用户信息，如果找到则返回一个包含重置密码页面URL的JSON数据，否则返回一个表示账号不存在的错误消息。





	@ResponseBody
	@RequestMapping("/admin/huiyuan/exit")
	public JsonResult exit() {

		if (request.getSession().getAttribute("huiyuan") != null) {

			System.out.println("系统退出");
			request.getSession().removeAttribute("huiyuan");

		}

		return JsonResult.success(1, "成功退出");


	}

	@ResponseBody
	@PostMapping("/admin/huiyuan/login")//POST请求的处理方法，路径为"/admin/huiyuan/login"。该方法用于处理用户登录请求。
	private JsonResult login() {

		String accountname = request.getParameter("accountname");
		String password = request.getParameter("password");

		String filter = MessageFormat.format("where accountname=''{0}'' and password=''{1}''", accountname,password);
		Huiyuan huiyuan = (Huiyuan) huiyuanSrv.load(filter);
		if (huiyuan == null)
			return JsonResult.error(-1, "系统账户和密码不匹配");
		if (!huiyuan.getPassword().equals(password))
			return JsonResult.error(-2, "密码错误");
		huiyuan.setLogtimes(huiyuan.getLogtimes() + 1);

		huiyuanSrv.update(huiyuan);
		request.getSession().setAttribute("huiyuan",huiyuan);
		return JsonResult.success(1, "成功登陆", huiyuan);

	}//处理用户登录请求。接收来自前端的账户名和密码，根据这些信息在数据库中查找匹配的用户记录。
//如果找到匹配的用户，则将其登录次数加一，并更新用户信息。最后，将用户信息存储在会话中，并返回一个表示成功登录的 JsonResult 对象。
	@ResponseBody
	@PostMapping("/admin/huiyuan/save")//处理POST请求，并且请求的路径是"/admin/huiyuan/save"。
	public JsonResult save() {
		String accountname = request.getParameter("accountname");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String idcardno = request.getParameter("idcardno");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		String touxiang = request.getParameter("touxiang");
		String sex = request.getParameter("sex");
		String des = request.getParameter("des");//从HTTP请求中获取参数值，使用了request.getParameter()方法。
		if (huiyuanSrv.isExist("where accountname='" + accountname + "'"))
			return JsonResult.error(-1, "用户名已经存在");//检查用户名是否已存在，如果已存在则返回一个带有错误信息的JsonResult对象。
		Huiyuan huiyuan = new Huiyuan();
		huiyuan.setAccountname(accountname == null ? "" : accountname);
		huiyuan.setPassword(password == null ? "" : password);
		huiyuan.setPaypwd(huiyuan.getPassword());
		if (mobile != null)
			huiyuan.setMobile(mobile);
		else
			huiyuan.setMobile(accountname);
		if (address != null)
			huiyuan.setAddress(address);
		if (sex != null)
			huiyuan.setSex(sex);
		else
			huiyuan.setSex("男");
		huiyuan.setNickname(accountname);
		huiyuan.setName(name);

        huiyuan.setTypeid(1);
		huiyuan.setRegdate(new Date());
		huiyuan.setIdcardno(idcardno == null ? "" : idcardno);
		huiyuan.setLogtimes(0);
		if (touxiang != null)
			huiyuan.setTouxiang(touxiang);
		else
			huiyuan.setTouxiang("\\upload\nopic.jpg");
		huiyuan.setEmail(email == null ? "" : email);
		huiyuan.setStatus(1);
		huiyuan.setYue(0.0);
		huiyuan.setJifen(0);
		huiyuan.setDes(des == null ? "" : des);
		huiyuanSrv.save(huiyuan);//创建了一个Huiyuan对象，设置了各个属性的值，最后调用了huiyuanSrv.save(huiyuan)方法保存会员信息到数据库中。
		return JsonResult.success(1, "注册成功");//如果保存操作成功，将返回一个表示成功的JsonResult对象，其中包含一个状态码和消息。

	}//接收来自前端的会员信息，保存到数据库中，并返回相应的结果，如果有错误则返回错误信息。

	@ResponseBody
	@PostMapping("/admin/huiyuan/delete")
	public JsonResult delete() {
		String[] ids = request.getParameterValues("ids");
		if (ids == null)
			return JsonResult.error(-1, "编号不能为空");
		String where = " where id in(" + String.join(",", ids) + ")";
		huiyuanSrv.delete(where);
		return JsonResult.success(1, "删除成功");
	}

	@ResponseBody
	@PostMapping("/admin/huiyuan/update")
	public JsonResult update() {
		String id = request.getParameter("id");
		if (id == null)
			return JsonResult.error(-1, "编号不能为空");
		Huiyuan huiyuan = huiyuanSrv.load(new Integer(id));
		if (huiyuan == null)
			return JsonResult.error(-2, "账号不存在");
		String accountname = request.getParameter("accountname");
		String nickname = request.getParameter("nickname");
		String touxiang = request.getParameter("touxiang");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String sex = request.getParameter("sex");
		String typeid=request.getParameter("typeid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String idcardno = request.getParameter("idcardno");
		String des=request.getParameter("des");
		if (accountname != null)
			huiyuan.setAccountname(accountname);
		huiyuan.setNickname(nickname == null ? "" : nickname);
		huiyuan.setTouxiang(touxiang == null ? "" : touxiang);
		huiyuan.setEmail(email == null ? "" : email);
		huiyuan.setMobile(mobile == null ? "" : mobile);
		huiyuan.setIdcardno(idcardno == null ? "" : idcardno);
		huiyuan.setSex(sex == null ? "" : sex);
		huiyuan.setAddress(address == null ? "" : address);
		huiyuan.setName(name == null ? "" : name);
		if(typeid!=null)
			huiyuan.setTypeid(new Integer(typeid));
		huiyuan.setDes(des==null?"":des);
		huiyuanSrv.update(huiyuan);
		return JsonResult.success(1, "更新成功");

	}

	@ResponseBody
	@PostMapping("/admin/huiyuan/modifypw")
	public JsonResult modifyPw() {

		String password = request.getParameter("password1");
		String repassword1 = request.getParameter("repassword1");
		String id = request.getParameter("id");
		if (id == null || id == "")
			return JsonResult.error(-1, "编号不存在");
		Huiyuan huiyuan = huiyuanSrv.load("where id=" + id);
		if (huiyuan == null)
			return JsonResult.error(-2, "不存在的账号");

		if (!huiyuan.getPassword().equals(password))
			return JsonResult.error(-3, "原始密码不正确");
		huiyuan.setPassword(repassword1);
		huiyuanSrv.update(huiyuan);
		return JsonResult.success(1, "成功修改");


	}


	@ResponseBody
	@PostMapping("/admin/huiyuan/load")
	public JsonResult load() {
		//
		String id = request.getParameter("id");
		if (id == null || id == "")
			return JsonResult.error(-1, "编号不存在");
		Huiyuan huiyuan = huiyuanSrv.load("where id=" + id);
		if (huiyuan == null)
			return JsonResult.error(-2, "不存在的账号");
		return JsonResult.success(1,"成功",huiyuan);

	}



	@ResponseBody
	@PostMapping("/admin/huiyuan/info")
	public JsonResult info() {
		//
		String id = request.getParameter("id");
		String accountname = request.getParameter("accountname");
		if (id == null && accountname == null)
			return JsonResult.error(-1, "参数不存在");

		Huiyuan huiyuan =null;
		if(id!=null&&id!=""){
		    huiyuan=huiyuanSrv.loadPlus(new Integer(id));
		  if (huiyuan == null)
			return JsonResult.error(-2, "不存在的账号");
		}
		if(accountname!=null&&accountname!=""){
			HashMap map = new HashMap();
			map.put("accountname",accountname);
			huiyuan=huiyuanSrv.loadPlus(map);
			if(huiyuan==null)
				return JsonResult.error(-2, "不存在的账号");
		}

		return JsonResult.success(1,"成功",huiyuan);

	}
/*
	@ResponseBody
	@PostMapping("/admin/huiyuan/updatejb")
	public JsonResult updatejb() {
		String id = request.getParameter("id");
		String typeid=request.getParameter("typeid");

		if (id == null)
			return JsonResult.error(-1,"id不能味浓");
		Huiyuan huiyuan = huiyuanSrv.load(new Integer(id));
		if (huiyuan == null)
			return JsonResult.error(-2,"数据非法");

		if(typeid!=null)
			huiyuan.setTypeid(Integer.parseInt(typeid));
		else
			huiyuan.setTypeid(1);
		huiyuanSrv.update(huiyuan);
		return JsonResult.success(1,"设置成功");

	}
*/

	@ResponseBody
	@PostMapping("/admin/huiyuan/list")//获取会员列表
	public JsonResult list() {
		int pageindex = 1;
		int pagesize = 10;//当前页码和每页显示的条目数
		// 获取当前分页
		String accountname = request.getParameter("accountname");
		HashMap map = new HashMap();
		if (accountname != null)
			map.put("accountname",accountname);//查询特定账号名的会员信息。

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

		List<Huiyuan> listHuiyuan = huiyuanSrv.getEntityPlus(map);
		return JsonResult.success(1,"获取会员信息",new PageInfo<>(listHuiyuan));
	}

	
	

}
