package com.daowen.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.HashMap;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.util.JsonResult;
import com.daowen.util.StringUtil;
import com.daowen.vo.CouponVo;
import com.daowen.vo.CreateOrderDTO;
import com.daowen.vo.OrderDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.zookeeper.server.quorum.FastLeaderElection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;
import org.springframework.web.bind.annotation.ResponseBody;

/**************************
 *
 * 订单控制
 *
 */
@Controller
public class ShorderController {
    @Autowired
    private ShorderService shorderSrv = null;
    @Autowired
    private HuiyuanService huiyuanSrv=null;
    @Autowired
    private OrderitemService orderitemSrv=null;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private  CourecordService courecordSrv=null;

    @Autowired
    private ShangpinService shangpinSrv=null;

    private String cartName = "shopcart";
    @Autowired
    private SpcommentService spcommentSrv=null;



    @ResponseBody
    @PostMapping("/admin/shorder/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1,"参数异常");
        for(String id : ids){
            shorderSrv.delete("where id="+id);
            orderitemSrv.delete("where orderid="+id);
        }
        return JsonResult.success(1,"操作成功");
    }




    @ResponseBody
    @PostMapping("/admin/shorder/saletongji")
    public JsonResult saleTongji() {

        String begdate = request.getParameter("begdate");
        String enddate = request.getParameter("enddate");
        HashMap<String,Object> map=new HashMap<>();
        if(begdate!=null)
            map.put("begdate",begdate);
        if(enddate!=null)
            map.put("enddate",enddate);
        List<HashMap<String,Object>> listMap=shorderSrv.saleStat(map);

        if(listMap!=null)
            request.setAttribute("listMap",listMap);

        return JsonResult.success(1,"成功",listMap);

    }




    @ResponseBody
    @PostMapping("/admin/shorder/shouhuo")
    public  JsonResult shouhuo(){
        String oiid=request.getParameter("oiid");
        String star=request.getParameter("star");
        String des=request.getParameter("des");
        String appraiserid=request.getParameter("appraiserid");
        if(oiid==null||oiid.equals(""))
            return JsonResult.error(-1,"参数异常");
        Orderitem orderitem=orderitemSrv.load("where id="+oiid);
        Spcomment spcomment=new Spcomment();
        spcomment.setCreatetime(new Date());
        spcomment.setSpid(orderitem.getSpid());
        spcomment.setOrderid(orderitem.getId());
        spcomment.setCresult(star==null?1:Integer.parseInt(star));
        spcomment.setAppraiserid(appraiserid==null?0:Integer.parseInt(appraiserid));
        spcomment.setDes(des);
        spcommentSrv.save(spcomment);
        int count=  orderitemSrv.executeUpdate(MessageFormat.format("update orderitem set state=4 where id={0}  ",oiid));
        int ndCount=orderitemSrv.getRecordCount(MessageFormat.format("where orderid={0,number,#} and state=3 ",orderitem.getOrderid()));
        //商品全部发货则更新订单为已发货状态
        if(ndCount==0)
            shorderSrv.executeUpdate(MessageFormat.format("update shorder set state=4 where id={0,number,#}",orderitem.getOrderid()));
        if(count>0)
            return JsonResult.success(1,"收货成功");
        return  JsonResult.error(-2,"收货失败");

    }

    @ResponseBody
    @PostMapping("/admin/shorder/payment")
    public JsonResult payment(){
        String id=request.getParameter("id");
        String crid=request.getParameter("crid");
        String paypwd=request.getParameter("paypwd");
        String usejifen=request.getParameter("usejifen");
        Boolean busejifen= false;
        if(usejifen!=null)
            busejifen=Boolean.parseBoolean(usejifen);
        Boolean.parseBoolean(usejifen);
        if(paypwd==null||paypwd.equals("")){
            return JsonResult.error(-1,"请输入支付密码");
        }
        if(id==null||paypwd.equals(""))
            return JsonResult.error(-2,"订单编号异常");
        if(!StringUtil.isNumeric(id))
            return JsonResult.error(-3,"订单编号异常");

        Shorder order=shorderSrv.load("where id="+id);
        if(order==null)
            return JsonResult.error(-4,"订单号不存在");
        Huiyuan huiyuan= huiyuanSrv.load("where id="+order.getPurchaser());
        if(huiyuan==null)
            return JsonResult.error(-5,"账户信息异常");

        if(!paypwd.equals(huiyuan.getPaypwd()))
            return JsonResult.error(-6,"支付密码不正确");

        double jine=order.getTotalfee();
//        if(busejifen){
//            jine= order.getTotalfee()-huiyuan.getJifen()/10;
//        }
        if(crid!=null) {
            CouponVo couponVo = courecordSrv.loadPlus(new Integer(crid));
            jine= order.getTotalfee()-couponVo.getFee();
            courecordSrv.executeUpdate(" update courecord set state =2 where id=" + crid);
        }
        int count= huiyuanSrv.deduct(order.getPurchaser(),jine);
        if(count<=0)
            return JsonResult.error(-7,"账户余额不足");
        //积分清零
        //huiyuanSrv.executeUpdate("update huiyuan set jifen=0 where id="+huiyuan.getId());

       //购买商品加积分
        huiyuanSrv.executeUpdate(MessageFormat.format("update huiyuan set jifen={0,number,#} where id={1,number,#}",order.getTotalfee(),huiyuan.getId()));

        Boolean res=shorderSrv.changeToPayed(order.getId());
        if(!res)
            return JsonResult.error(-8,"系统异常");

        return JsonResult.success(1,"付款成功");


    }

    @ResponseBody
    @PostMapping("/admin/shorder/cancel")
    public JsonResult cancel(){
        String id=request.getParameter("id");

        if(!StringUtil.isNumeric(id))
            return JsonResult.error(-1,"订单编号异常");

        shorderSrv.executeUpdate("update shorder set state=5 where id="+id);
        orderitemSrv.executeUpdate("update  orderitem set state=5 where orderid="+id);

        return JsonResult.success(1,"付款成功");
    }

    @ResponseBody
    @PostMapping("/admin/shorder/dispatch")
    public JsonResult dispatch(){
        String id=request.getParameter("id");
        String zyid=request.getParameter("zyid");

        if(!StringUtil.isNumeric(id))
            return JsonResult.error(-1,"订单编号异常");
        Shorder order=shorderSrv.load("where id="+id);
        if(order==null)
            return JsonResult.error(-2,"订单异常");
        if(zyid==null)
            return JsonResult.error(-3,"请选择送餐人");

        order.setZyid(new Integer(zyid));
        //已分配送餐员
        order.setState(8);
        shorderSrv.update(order);

        orderitemSrv.executeUpdate("update  orderitem set state=8 where orderid="+id);

        return JsonResult.success(1,"分配成功");


    }


    @ResponseBody
    @PostMapping({"/admin/shorder/info","/admin/shorder/load"})
    public JsonResult info(){
        String id=request.getParameter("id");
        String state=request.getParameter("state");
        if(id==null||id=="")
            return JsonResult.error(-1,"需要id参数");
        if(!StringUtil.isNumeric(id)){
            return JsonResult.error(-2,"id参数应该为数字");
        }
        HashMap map=new HashMap<String,Object>();
        if(id!=null)
            map.put("id",id);
        if(state!=null)
            map.put("state",state);
        OrderDTO order = shorderSrv.loadPlus(map);
        return JsonResult.success(1,"获取订单成功",order);

    }

    @ResponseBody
    @PostMapping("/admin/shorder/deliver")
    public  JsonResult deliver(){
        String oiid=request.getParameter("oiid");
        String wlno=request.getParameter("wlno");
        if(oiid==null||oiid.equals(""))
            return JsonResult.error(-1,"参数异常");
        Orderitem oi=orderitemSrv.load("where id="+oiid);
        oi.setState(3);
        oi.setWlno(wlno);
        int count=  orderitemSrv.update(oi);
        int ndCount=orderitemSrv.getRecordCount(MessageFormat.format("where orderid={0,number,#} and state=2 ",oi.getOrderid()));
        //商品全部发货则更新订单为已发货状态
        if(ndCount==0)
            shorderSrv.executeUpdate(MessageFormat.format("update shorder set state=3 where id={0,number,#}",oi.getOrderid()));
        if(count>0)
            return JsonResult.success(1,"发货成功");
        return  JsonResult.error(-2,"发货失败");

    }

    @ResponseBody
    @PostMapping("/admin/shorder/save")
    public JsonResult  save(){
        String payload=getRequestPayload(request);
        System.out.println("payload="+payload);
        CreateOrderDTO orderDTO = JSONObject.parseObject(payload, CreateOrderDTO.class);
        JsonResult vr= shorderSrv.validateStock(orderDTO);
        if(vr.getStateCode()<0)
            return vr;
         return shorderSrv.createOrder(orderDTO);

    }



    @ResponseBody
    @PostMapping("/admin/shorder/shlist")
    public JsonResult shlist(){
        String publisher=request.getParameter("publisher");
        String state=request.getParameter("state");

        if(publisher==null||publisher=="")
            return JsonResult.error(-1,"请提供商户账号");

        HashMap map=new HashMap<String,Object>();
        if(publisher!=null)
            map.put("publisher",publisher);
        if(state!=null)
            map.put("state",state);

        List<OrderDTO> purchaseOrder = shorderSrv.getEntityPlus(map);
        return JsonResult.success(1,"获取订单成功",purchaseOrder);

    }


	@ResponseBody
	@PostMapping("/admin/shorder/list")
	public JsonResult list(){
    	String purchaser=request.getParameter("purchaser");
    	String state=request.getParameter("state");
        String ddno = request.getParameter("ddno");
        String zyid=request.getParameter("zyid");


        HashMap map=new HashMap<String,Object>();//创建了一个HashMap对象，用于存储请求参数。
    	if(purchaser!=null){
            if(purchaser!=null&&!StringUtil.isNumeric(purchaser)){
                return JsonResult.error(-2,"参数应该为数字");
            }
           map.put("purchaser",purchaser);
    	}
        if(zyid!=null)
            map.put("zyid",zyid);
    	if(state!=null)
    	    map.put("state",state);
        if (ddno != null)
            map.put("ddno",ddno);
        int pageindex = 1;
        int pagesize = 10;
        // 获取当前分页
        String currentpageindex = request.getParameter("currentpageindex");
        // 当前页面面积
        String currentpagesize = request.getParameter("pagesize");
        PageHelper.startPage(pageindex,pagesize);
        List<OrderDTO> listshorder = shorderSrv.getEntityPlus(map);
        request.setAttribute("listshorder", listshorder);
        PageInfo<OrderDTO> pageInfo=new PageInfo<>(listshorder);
    	return JsonResult.success(1,"获取订单成功",pageInfo);

    }//根据请求中传递的菜单名称查询条件，获取相应的级联菜单数据，并以JSON格式返回给客户端。

	private String getRequestPayload(HttpServletRequest request){
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String line="";
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		}catch (IOException e){

		}
		return sb.toString();
	}



}
