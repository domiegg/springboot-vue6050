package com.daowen.controller;

import javax.servlet.http.HttpSession;

import com.daowen.entity.Huiyuan;
import com.daowen.service.HuiyuanService;
import com.daowen.util.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.daowen.entity.Users;
import com.daowen.service.UsersService;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.HashMap;

@Controller
public class AdminController extends SimpleController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private HuiyuanService huiyuanSrv=null;

	@ResponseBody//处理登录请求的方法，根据请求参数中的账户类型（usertype）来执行相应的登录操作，并返回相应的结果。
	@PostMapping("/admin/admin/login")//前端路径
	public JsonResult login() {//通过账户类型来区分不同的登录方式
		String usertype = request.getParameter("usertype");//通过request.getParameter("usertype")获取请求参数中的usertype值。然后通过一系列的条件判断，根据不同的usertype值调用不同的登录方法。

		if (usertype != null && usertype.equals("1")) {
			return adminLogin(1);//如果账户类型是"1"，则调用adminLogin(1)方法。
		}

		if (usertype != null && usertype.equals("3")) {
			return adminLogin(2);
		}

		if (usertype != null && usertype.equals("2")) {
			return huiyuanLogin();
		}//如果账户类型不是以上三种情况，则返回一个带有错误信息的JsonResult对象，表示账户类型不正确。


		return JsonResult.error(-2,"账户类型不对");
	}//根据不同的账户类型来执行相应的登录操作，并返回相应的结果。




	private JsonResult huiyuanLogin() {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
        HashMap map = new HashMap();
        map.put("accountname",username);
		Huiyuan u = huiyuanSrv.loadPlus(map);
		if (u == null)
			return JsonResult.error(-1,"不合法账户");
		if(!password.equals(u.getPassword()))
			return JsonResult.error(-1,"账号密码不匹配");
		HttpSession session = request.getSession();
		session.setAttribute("huiyuan", u);
		return  JsonResult.success(1,"登录成功",u);

	}//会员登录的验证和会话管理功能。



	private JsonResult adminLogin(int type) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HashMap<String,Object> map=new HashMap<>();
		map.put("username",username);
		Users u = usersService.loadPlus(map);
		if (u == null) {
			return JsonResult.error(-1,"用户名不存在");
		}

		if (password!=null&&!password.equals(u.getPassword())) {
			return JsonResult.error(-3,"用户名和密码不匹配");
		}
		if(u.getRoleid()!=type){
			return JsonResult.error(-3,"用户类型不对");
		}
		usersService.executeUpdate("update users set logtimes=logtimes+1 where id="+u.getId());
		return JsonResult.success(1,"登录成功",u);
	}




	@PostMapping("/admin/admin/exit")
	@ResponseBody
	public JsonResult exit(){
		String usertype=request.getParameter("usertype");
		if(usertype!=null&&usertype.equals("1")) {
			if (request.getSession().getAttribute("users") != null) {
				System.out.println("系统退出");
				request.getSession().removeAttribute("users");
			}
		}

		if(usertype!=null&&usertype.equals("2")) {
			if (request.getSession().getAttribute("huiyuan") != null) {
				System.out.println("系统退出");
				request.getSession().removeAttribute("huiyuan");
			}
		}




		return  JsonResult.success(1,"退出成功");
	}



}
