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
public  class    ChongzhirecController extends SimpleController{
  
       @Autowired
	private ChongzhirecService  chongzhirecSrv = null;

	
         //充值
       @PostMapping("/admin/chongzhirec/save")
	public  JsonResult  save(){
	    
		 String ddno=request.getParameter("ddno");
             
		 String hyid=request.getParameter("hyid");
             
		 String amount=request.getParameter("amount");
             
		 String createtime=request.getParameter("createtime");
             
		 String state=request.getParameter("state");
             
		 String paytype=request.getParameter("paytype");
             
	       SimpleDateFormat  sdfchongzhirec=new SimpleDateFormat("yyyy-MM-dd");
             Chongzhirec chongzhirec=new Chongzhirec();
	      
		              
				      chongzhirec.setDdno(ddno==null?"":ddno);
				
				
				
				
             
		              
				
				      chongzhirec.setHyid(hyid==null?0:new Integer(hyid));
				
				
				
             
		              
				
				      chongzhirec.setAmount(amount==null?0:new Integer(amount));
				
				
				
             
		              
				
				
				
				   if(createtime!=null){
					  try {
							chongzhirec.setCreatetime(sdfchongzhirec.parse(createtime));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					}else{
						chongzhirec.setCreatetime(new Date());
					}
				
             
		              
				
				      chongzhirec.setState(state==null?0:new Integer(state));
				
				
				
             
		              
				
				      chongzhirec.setPaytype(paytype==null?0:new Integer(paytype));
				
				
				
             //end forEach
              
                    Boolean validateresult=chongzhirecSrv.isExist( "  where  ddno='"+ddno+"'" );
		     if(validateresult)
		        return JsonResult.error(-1,"已存在的记录");
              
		  chongzhirecSrv.save(chongzhirec);
		  return  JsonResult.success(1,"成功",chongzhirec);
	}
	@PostMapping("/admin/chongzhirec/update")
	public  JsonResult  update(){
	    
		String id=request.getParameter("id");
		if(id==null)
			   return JsonResult.error(-1,"ID不能为空");
		Chongzhirec  chongzhirec=chongzhirecSrv.load("where id="+id);
		if(chongzhirec==null)
			 return JsonResult.error(-2,"非法数据");
               
		     String ddno=request.getParameter("ddno");
              
		     String hyid=request.getParameter("hyid");
              
		     String amount=request.getParameter("amount");
              
		     String createtime=request.getParameter("createtime");
              
		     String state=request.getParameter("state");
              
		     String paytype=request.getParameter("paytype");
              
	       SimpleDateFormat  sdfchongzhirec=new SimpleDateFormat("yyyy-MM-dd");
	     
		              
				      chongzhirec.setDdno(ddno==null?"":ddno);
				
				
				
				
		
		              
				
				      chongzhirec.setHyid(hyid==null?0:new Integer(hyid));
				
				
				
		
		              
				
				      chongzhirec.setAmount(amount==null?0:new Integer(amount));
				
				
				
		
		              
				
				
				
				   if(createtime!=null){
					  try {
							chongzhirec.setCreatetime(sdfchongzhirec.parse(createtime));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					}else{
						chongzhirec.setCreatetime(new Date());
					}
				
		
		              
				
				      chongzhirec.setState(state==null?0:new Integer(state));
				
				
				
		
		              
				
				      chongzhirec.setPaytype(paytype==null?0:new Integer(paytype));
				
				
				
		
		chongzhirecSrv.update(chongzhirec);
		  return  JsonResult.success(1,"成功",chongzhirec);
		
	}
	@PostMapping("/admin/chongzhirec/delete")
	public  JsonResult  delete(){
	        String[] ids = request.getParameterValues("ids");
		if (ids == null)
			return JsonResult.error(-1,"ids不能为空");
		String spliter = ",";
		String where = " where id  in(" + join(spliter, ids)+ ")";
		chongzhirecSrv.delete(where);
		return  JsonResult.success(1,"不能为空");
	}
	@RequestMapping("/admin/chongzhirec/load")
	public  JsonResult  load(){
	            String id=request.getParameter("id");
		
		    if(id==null) 
		           return JsonResult.error(-1,"ID不能为空");
		     Chongzhirec  chongzhirec=chongzhirecSrv.loadPlus(new Integer(id));
		     if(chongzhirec==null) 
			 return JsonResult.error(-2,"非法数据");
		     return  JsonResult.success(1,"成功",chongzhirec);
		 
	}
	
	@RequestMapping("/admin/chongzhirec/info")
	public  JsonResult  info(){
	            String id=request.getParameter("id");
		
		    if(id==null) 
		           return JsonResult.error(-1,"ID不能为空");
		     Chongzhirec  chongzhirec=chongzhirecSrv.loadPlus(new Integer(id));
		     if(chongzhirec==null) 
			 return JsonResult.error(-2,"非法数据");
		     return  JsonResult.success(1,"成功",chongzhirec);
		 
	}
	@PostMapping("/admin/chongzhirec/list")
	public  JsonResult  list(){
	     
	      HashMap<String,Object>  map=new HashMap<>();
	       String ispaged=request.getParameter("ispaged");
	      
	              String ddno=request.getParameter("ddno");
	               if(ddno!=null)
	                  map.put("ddno",ddno);
	      
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
	                 PageHelper.startPage(pageindex,pagesize);
			List<Chongzhirec> listChongzhirec = chongzhirecSrv.getEntityPlus(map);
			PageInfo<Chongzhirec> pageInfo=new PageInfo<Chongzhirec>(listChongzhirec);
			return JsonResult.success(1,"成功",pageInfo);
		}
		 return JsonResult.success(1,"获取成功", chongzhirecSrv.getEntityPlus(map));

	   
          }
	
	//##{{methods}}


}
