package com.daowen.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.entity.Huiyuan;
import com.daowen.entity.Lanmu;
import com.daowen.service.LanmuService;
import com.daowen.service.ShangpinService;
import com.daowen.util.JsonResult;
import com.daowen.vo.LanmuVo;
import com.daowen.vo.ShangpinVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.daowen.entity.Shangpin;
import com.daowen.ssm.simplecrud.SimpleController;

@RestController
public class ShangpinController extends SimpleController {



	@PostMapping("/admin/shangpin/mylanmu")
	public JsonResult mylanmu(){
		List<LanmuVo> listLanmu=shangpinSrv.getMyLanmus();
		return JsonResult.success(1,"获取栏目",listLanmu);
	}


	@PostMapping("/admin/shangpin/list")
	public JsonResult list(){

		String spname = request.getParameter("name");
		String pubren = request.getParameter("pubren");
		String typeid = request.getParameter("typeid");
		String subtypeid = request.getParameter("subtypeid");
		String tagid=request.getParameter("tagid");
		HashMap<String,Object> map=new HashMap();
		if (spname != null)
			map.put("spname",spname);
		if (pubren != null)
			map.put("pubren",pubren);
		if (typeid != null)
			map.put("typeid",typeid);
		if(tagid!=null)
			map.put("tagid",tagid);
		if(subtypeid!=null)
			map.put("subtypeid",subtypeid);
		map.put("order","order by id desc");
		int pageindex = 1;
		int pagesize = 10;
		// 获取当前分页
		String currentpageindex = request.getParameter("currentpageindex");
		// 当前页面面积
		String currentpagesize = request.getParameter("pagesize");
		// 设置当前页
		if (currentpageindex != null)
			pageindex = new Integer(currentpageindex);
		// 设置当前页面积
		if (currentpagesize != null)
			pagesize = new Integer(currentpagesize);
		PageHelper.startPage(pageindex,pagesize);
		List<ShangpinVo> listshangpin = shangpinSrv.getEntityPlus(map);
		PageInfo<ShangpinVo>  pageInfo=new PageInfo<>(listshangpin);
		return JsonResult.success(1,"获取商品信息",pageInfo);

	}


	@PostMapping("/admin/shangpin/hotclick")
	public JsonResult hotclick(){
		HashMap map=new HashMap();
		map.put("order","order by clickcount desc");
		map.put("topcount",5);
		List<ShangpinVo> listShangpin=shangpinSrv.getEntityPlus(map);
		return JsonResult.success(1,"热销产品",listShangpin);

	}



	@PostMapping("/admin/shangpin/count")
	public JsonResult counttongji(){
		String sql=" select lm.name ,count(*) as value from shangpin sp,lanmu lm where lm.id=sp.typeid ";
		String pubren=request.getParameter("pubren");
		if(pubren!=null)
			sql+=" and sp.pubren="+pubren;
		sql+=" group by sp.typeid,lm.name ";
		List<HashMap<String,Object>> listTongji=shangpinSrv.queryToMap(sql);
		return  JsonResult.success(1,"",listTongji);
	}


	@PostMapping("/admin/shangpin/saledstat")
	public JsonResult saleStat(){
		String sql=" select sp.name as name,sum(count) as value from shangpin sp,orderitem oi where sp.id=oi.spid  ";
		String pubren=request.getParameter("pubren");
		if(pubren!=null)
			sql+=" and sp.pubren="+pubren;
		sql+=" group by sp.id,sp.name ";
		List<HashMap<String,Object>> listTongji=shangpinSrv.queryToMap(sql);
		return  JsonResult.success(1,"",listTongji);
	}


	@PostMapping("/admin/shangpin/hotsales")//查询数据库中销量最高的前10个商品，并以JSON格式返回给客户端。
	public JsonResult hotSales(){

		String sql=" select  sp.*,t.saledcount from shangpin sp  inner join (select oi.spid,sum(oi.count) as saledcount  from shorder o ,orderitem oi where o.id=oi.orderid  and o.state!=1 group by oi.spid) t on t.spid=sp.id  order by saledcount desc limit 10 ";
		List<HashMap<String,Object>> listMap=shangpinSrv.queryToMap(sql);
		return JsonResult.success(1,"热销产品",listMap);

	}//通过联合查询获取商品表中的商品信息以及销售订单表和订单项表中的销售数量。


	@GetMapping("/admin/shangpin/type")
	public  JsonResult getTypes(){
		List<Lanmu> listLanmu=lanmuSrv.getEntity("where type=2");
		return JsonResult.success(1,"获取 商品类别",listLanmu);
	}


	@RequestMapping("/admin/shangpin/info")//处理获取商品信息，收来自前端的商品ID和会员ID，并根据这些参数从数据库中获取对应的商品信息。
	public JsonResult info() {
		String id = request.getParameter("id");
		String hyid=request.getParameter("hyid");//从请求参数中获取商品ID和会员ID。
		ShangpinVo shangpin = null;
		if (id == null) {
			return JsonResult.error(-1, "参数异常");
		}//如果商品ID为空，则返回参数异常的错误信息。
		Huiyuan temhy=(Huiyuan)request.getSession().getAttribute("huiyuan");//从会话中获取当前登录的会员信息。
		shangpinSrv.view(temhy,id);//根据商品ID加载商品信息。
		shangpin = shangpinSrv.loadPlus(Integer.parseInt(id));
		if(hyid!=null){
			shangpinSrv.executeUpdate("update shangpin set clickcount=clickcount+1 where id="+id);
		}//如果会员ID不为空，则执行更新商品点击量的操作。
		return JsonResult.success(1, "获取 菜品信息", shangpin);//将获取到的商品信息封装成 JsonResult 对象，并返回给前端。
	}

	@PostMapping("/admin/shangpin/recomment")
	public JsonResult recomment(){
		String id=request.getParameter("id");
		if(id==null)
			return JsonResult.error(-1,"id不能为空");
		Huiyuan temhy=(Huiyuan)request.getSession().getAttribute("huiyuan");
		if(temhy==null)
			return JsonResult.error(-2,"没有登录");
		//获取用户浏览记录
//		List<ShangpinVo> listRecomment=shangpinSrv.getRecomment(request,temhy);
		List<ShangpinVo> listRecomment=shangpinSrv.getRecomment2(temhy,new Integer(id));
		return JsonResult.success(1,"猜你喜欢",listRecomment);
	}


	public void shenpi() {
		String id = request.getParameter("id");
		String forwardurl = request.getParameter("forwardurl");
		String spstate = request.getParameter("spstate");
		String reply = request.getParameter("reply");
		String shenpiren = request.getParameter("shenpiren");
		int statuscode = 3;
		// 验证错误url
		String errorurl = request.getParameter("errorurl");
		if (id == null)
			return;
		Shangpin shangpin = shangpinSrv.load(" where id=" + id);
		if (shangpin == null)
			return;
		if (spstate != null)
			statuscode = Integer.parseInt(spstate);
		shangpin.setSpstate(statuscode);
		shangpinSrv.update(shangpin);
		if (forwardurl == null) {
			forwardurl = "/admin/shangpinmanager.do?actiontype=get";
		}
		redirect(forwardurl);
	}


	@PostMapping("/admin/shangpin/shangjia")//上架商品
	public JsonResult shangjia() {//封装操作结果和数据。



		String[] ids = request.getParameterValues("ids");//指定要上架的商品的ID数组
		if (ids == null)
			return JsonResult.error(-1,"参数异常");
		String spliter = ",";
		String sql = " update shangpin set state=1 where id in("
				+ join(spliter, ids) + ")";

		int  count=shangpinSrv.executeUpdate(sql);//执行SQL语句，将指定ID的商品状态更新为上架状态，并获取更新的记录数。
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("count",count);
		return JsonResult.success(1,"上架成功",jsonObject);
	}//根据请求中传递的商品ID数组，将对应的商品状态更新为上架状态，并返回更新结果给客户端。

	@PostMapping("/admin/shangpin/xiajia")
	public JsonResult xiajia() {

		String[] ids = request.getParameterValues("ids");
		if (ids == null)
			return JsonResult.error(-1,"参数异常");
		String spliter = ",";
		String sql = " update shangpin set state=2 where id in("
				+ join(spliter, ids) + ")";
		 int  count=shangpinSrv.executeUpdate(sql);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("count",count);
		return JsonResult.success(1,"下架成功",jsonObject);
	}

	@PostMapping("/admin/shangpin/addstock")//增加商品库存
	public JsonResult addStock() {
		String  id=request.getParameter("id");
		if (id == null)
			return JsonResult.error(-1,"id不能为空");
		Shangpin shangpin = shangpinSrv.load(new Integer(id));
		if (shangpin == null)
			return JsonResult.error(-2,"数据非法");
		String shuliang = request.getParameter("shuliang");
		String danwei = request.getParameter("danwei");

		if (shuliang != null) {
			shangpin.setKucun(shangpin.getKucun() + Integer.parseInt(shuliang));
			shangpin.setDanwei(danwei);
			shangpinSrv.update(shangpin);
		}

		return JsonResult.success(1,"成功");

	}//根据请求中传递的商品ID、增加的库存数量和计量单位来更新商品的库存信息，并返回操作结果给客户端



	@PostMapping("/admin/shangpin/delete")
	public JsonResult delete() {
		String[] ids = request.getParameterValues("ids");
		if (ids == null)
			return JsonResult.error(-1,"参数异常");
		String spliter = ",";
		String sql = " where id in(" + join(spliter, ids)+ ")";
		int count=shangpinSrv.delete(sql);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("count",count);
		return JsonResult.success(1,"删除成功",jsonObject);
	}


	@PostMapping("/admin/shangpin/cascadelanmu")
	public JsonResult cascadelanmu(){

		List<LanmuVo> listLanmu = shangpinSrv.getMyLanmus();
		JSONArray jsonArray=new JSONArray();
		if(listLanmu!=null)
			listLanmu.forEach(c->{
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("value",c.getId());
				jsonObject.put("label",c.getName());
				if(c.getSubtypes()!=null){
					JSONArray array = new JSONArray();
					c.getSubtypes().forEach(subtype->{
						JSONObject cobj=new JSONObject();
						cobj.put("label",subtype.getName());
						cobj.put("value",subtype.getId());
						array.add(cobj);
						jsonObject.put("children",array);
					});
				}
				jsonArray.add(jsonObject);
			});
		return JsonResult.success(1,"获取成功",jsonArray);
	}






	@PostMapping("/admin/shangpin/save")
	public JsonResult save() {

		String name = request.getParameter("name");
		String jiage = request.getParameter("jiage");
		String tuijian = request.getParameter("tuijian");
		String typeid = request.getParameter("typeid");
		String subtypeid = request.getParameter("subtypeid");
		String tupian = request.getParameter("tupian");
		String jieshao = request.getParameter("jieshao");
		String pubren = request.getParameter("pubren");
		String subtitle = request.getParameter("subtitle");
		String discount = request.getParameter("discount");
		String chandi = request.getParameter("chandi");
		String tagid = request.getParameter("tagid");
		String danwei=request.getParameter("danwei");
		SimpleDateFormat sdfshangpin = new SimpleDateFormat("yyyy-MM-dd");
		Shangpin shangpin = new Shangpin();
		shangpin.setName(name == null ? "" : name);
		shangpin.setJiage(jiage == null ? (double) 0 : new Double(jiage));
		shangpin.setTuijian(tuijian == null ? 0 : new Integer(tuijian));
		shangpin.setTypeid(typeid == null ? 0 : new Integer(typeid));
		shangpin.setSubtypeid(subtypeid == null ? 0 : new Integer(subtypeid));
		shangpin.setTupian(tupian == null ? "" : tupian);
		shangpin.setSpstate(2);
		shangpin.setKucun(10);
		shangpin.setClickcount(0);
		shangpin.setJieshao(jieshao == null ? "" : jieshao);
		shangpin.setChandi(chandi == null ? "" : chandi);
		shangpin.setSubtitle(subtitle == null ? "" : subtitle);
		if (discount != null){
			BigDecimal bdJiage=new BigDecimal(shangpin.getJiage());
			BigDecimal bdDiscount=new BigDecimal(discount);
			BigDecimal  bdhyjia= bdJiage.multiply(bdDiscount);
			bdhyjia.doubleValue();
			shangpin.setHyjia(bdhyjia.doubleValue());
		}
		else
			shangpin.setHyjia(shangpin.getJiage());

		shangpin.setState(1);
		shangpin.setDanwei(danwei==null?"":danwei);
		shangpin.setPubtime(new Date());
		shangpin.setPubren(pubren == null ? 0 : Integer.parseInt(pubren));
		shangpin.setTagid(tagid == null ? 0 : Integer.parseInt(tagid));
		shangpinSrv.save(shangpin);
		return JsonResult.success(1,"保存成功");

	}

	@PostMapping("/admin/shangpin/update")
	public JsonResult update() {
		String forwardurl = request.getParameter("forwardurl");
		String errorurl = request.getParameter("errorurl");
		String id = request.getParameter("id");
		if (id == null)
			return JsonResult.error(-1,"id不能为空");
		Shangpin shangpin = shangpinSrv.load(new Integer(id));
		if (shangpin == null)
			return JsonResult.error(-2,"非法数据");
		String name = request.getParameter("name");
		String jiage = request.getParameter("jiage");
		String tuijian = request.getParameter("tuijian");
		String typeid = request.getParameter("typeid");
		String subtypeid = request.getParameter("subtypeid");
		String tupian = request.getParameter("tupian");
		String jieshao = request.getParameter("jieshao");
		String pubren = request.getParameter("pubren");
		String subtitle = request.getParameter("subtitle");
		String discount = request.getParameter("discount");
		String danwei=request.getParameter("danwei");
		String chandi = request.getParameter("chandi");
		String tagid = request.getParameter("tagid");
		DecimalFormat df= new DecimalFormat("#.00");
		shangpin.setName(name);
		shangpin.setJiage(jiage == null ? (double) 0 : new Double(jiage));
		shangpin.setTuijian(tuijian == null ? 0 : new Integer(tuijian));
		shangpin.setSubtitle(subtitle == null ? "" : subtitle);
		shangpin.setTupian(tupian);
		shangpin.setJieshao(jieshao);
		shangpin.setTypeid(typeid == null ? 0 : new Integer(typeid));
		shangpin.setSubtypeid(subtypeid == null ? 0 : new Integer(subtypeid));
		shangpin.setDanwei(danwei==null?"":danwei);
		shangpin.setChandi(chandi == null ? "" : chandi);
		if (discount != null){
			BigDecimal bdJiage=new BigDecimal(shangpin.getJiage());
			BigDecimal bdDiscount=new BigDecimal(discount);
			BigDecimal  bdhyjia= bdJiage.multiply(bdDiscount);
			bdhyjia.doubleValue();
			shangpin.setHyjia(bdhyjia.doubleValue());
		}
		else
			shangpin.setHyjia(shangpin.getJiage());
		shangpin.setPubtime(new Date());
		shangpin.setTagid(tagid == null ? 0 : Integer.parseInt(tagid));
		shangpinSrv.update(shangpin);

		return JsonResult.success(1,"更新成功");

	}

	@RequestMapping("/admin/shangpin/load")
	public  JsonResult  load(){
		String id=request.getParameter("id");

		if(id==null)
			return JsonResult.error(-1,"ID不能为空");
		Shangpin  shangpin=shangpinSrv.loadPlus(new Integer(id));
		if(shangpin==null)
			return JsonResult.error(-2,"非法数据");
		return  JsonResult.success(1,"成功",shangpin);

	}



	@Autowired
	private ShangpinService shangpinSrv = null;
	@Autowired
	private LanmuService lanmuSrv = null;



}
