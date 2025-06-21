package com.daowen.controller;

import java.util.List;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
import com.daowen.vo.LanmuContentVo;
import com.daowen.vo.LanmuVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.scripts.JS;
import org.apache.tomcat.util.buf.C2BConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;

/**************************
 *
 * 板块管理控制
 *
 */
@RestController
public class LanmuController extends SimpleController {





    @PostMapping("/admin/lanmu/load")
    public JsonResult load() {
        String id=request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"参数异常");
        LanmuVo lanmu=lanmuSrv.loadPlus(new Integer(id));

        return JsonResult.success(1,"获取板块信息成功",lanmu);
    }


    @PostMapping("/admin/lanmu/info")
    public JsonResult info() {
        String id=request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"参数异常");
        LanmuVo lanmu=lanmuSrv.loadPlus(new Integer(id));

        return JsonResult.success(1,"获取板块信息成功",lanmu);
    }




    @PostMapping("/admin/lanmu/delete")
    public JsonResult delete() {
        String id=request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"参数异常");

        lanmuSrv.deleteLanmu(Integer.parseInt(id));

        return JsonResult.success(1,"删除成功");
    }



    @PostMapping("/admin/lanmu/save")
    public JsonResult save() {

        //获取名称
        String name = request.getParameter("name");
        String bannerurl=request.getParameter("bannerurl");
        String type=request.getParameter("type");
        SimpleDateFormat sdflanmu = new SimpleDateFormat("yyyy-MM-dd");
        Lanmu lanmu = new Lanmu();
        // 设置名称
        lanmu.setName(name == null ? "" : name);
        lanmu.setBannerurl(bannerurl==null?"":bannerurl);
        if(type!=null)
            lanmu.setType(Integer.parseInt(type));
        else
            lanmu.setType(1);

        //产生验证
        Boolean validateresult = lanmuSrv.isExist("where name='" + name + "'");
        if (validateresult) {
           return JsonResult.error(-1,"已存在的板块名称");
        }
        lanmuSrv.save(lanmu);
        return JsonResult.success(1,"保存成功");
    }
    @PostMapping("/admin/lanmu/update")
    public JsonResult update() {
        String forwardurl = request.getParameter("forwardurl");
        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"id不能为空");
        Lanmu lanmu = lanmuSrv.load(new Integer(id));
        if (lanmu == null)
            return JsonResult.error(-2,"不合法的数据");
        //获取名称
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String bannerurl=request.getParameter("bannerurl");
        SimpleDateFormat sdflanmu = new SimpleDateFormat("yyyy-MM-dd");
        if(type!=null)
            lanmu.setType(Integer.parseInt(type));
        else
            lanmu.setType(1);
        lanmu.setName(name);
        lanmu.setBannerurl(bannerurl==null?"":bannerurl);
        lanmuSrv.update(lanmu);
        return JsonResult.success(1,"更新成功");
    }


    @PostMapping("/admin/lanmu/list")//获取栏目信息列表
    public JsonResult list() {

        HashMap<String,Object> map=new HashMap<>();//创建一个HashMap对象用于存储查询条件

        String name = request.getParameter("name");//从请求参数中获取栏目名称(name)，如果存在则将其放入map中。

        if (name != null)
            map.put("name",name);
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
            pagesize = new Integer(currentpagesize);//设置默认的分页页码和页面尺寸，然后从请求参数中获取当前页码(currentpageindex)和页面尺寸(pagesize)，如果存在则更新对应的值。

        List<LanmuVo> listlanmu = lanmuSrv.getEntityPlus(map);//调用lanmuSrv的getEntityPlus方法，传入map作为查询条件，获取栏目信息列表。

        return JsonResult.success(1,"获取板块信息",listlanmu);//回查询到的栏目信息列表的JsonResult对象。
    }

    @PostMapping("/admin/lanmu/cascadelist")//POST请求的映射，表示当客户端发送一个POST请求到"/admin/lanmu/cascadelist"时，将由这个方法来处理。
    public JsonResult cascadelist(){
        HashMap<String,Object> map=new HashMap<>();//创建一个HashMap对象，用于存储查询条件。

        String name = request.getParameter("name");//从请求中获取名为“name”的参数值，表示菜单名称的查询条件。

        if (name != null)
            map.put("name",name);//如果获取到了name参数，则将其添加到map中作为查询条件。
        List<LanmuVo> listLanmu = lanmuSrv.getEntityPlus(map);//调用lanmuSrv的getEntityPlus方法，根据查询条件获取菜单数据列表listLanmu。
        JSONArray jsonArray=new JSONArray();//创建一个JSONArray对象，用于存储栏目数据的JSON格式。
        if(listLanmu!=null)
            listLanmu.forEach(c->{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",c.getId());
                jsonObject.put("value",c.getId());
                jsonObject.put("label",c.getName());
                jsonObject.put("name",c.getName());
                jsonObject.put("type",c.getType());
                jsonObject.put("datatype",1);
                if(c.getSubtypes()!=null){
                    JSONArray array = new JSONArray();
                    c.getSubtypes().forEach(subtype->{
                        JSONObject cobj=new JSONObject();
                        cobj.put("id",subtype.getId());
                        cobj.put("label",subtype.getName());
                        cobj.put("value",subtype.getId());
                        cobj.put("name",subtype.getName());
                        cobj.put("parentid",c.getId());
                        cobj.put("datatype",2);
                        array.add(cobj);
                        jsonObject.put("children",array);
                    });
                }
                jsonArray.add(jsonObject);//对listLanmu进行遍历，将每个栏目对象转换为JSON格式，并添加到jsonArray中。
            });

        return JsonResult.success(1,"获取成功",jsonArray);



    }//根据请求中传递的菜单名称查询条件，获取相应的级联菜单数据，并以JSON格式返回给客户端。


    @GetMapping("/admin/lanmu/content/{id}")
    public JsonResult getContent(@PathVariable("id") String id){
        if(id==null||id=="")
            return JsonResult.error(-1,"板块编号不合法");
        int nId=Integer.parseInt(id);
        LanmuContentVo lanmuVo=lanmuSrv.getContent(nId);
        if(lanmuVo==null||lanmuVo.getContent()==null)
            return JsonResult.error(-2,"没有找到板块内容");

        return JsonResult.success(1,"",lanmuVo);

    }


    @Autowired
    private LanmuService lanmuSrv = null;


}
