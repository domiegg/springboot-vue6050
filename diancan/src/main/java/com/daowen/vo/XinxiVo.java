package com.daowen.vo;

import com.daowen.entity.Xinxi;
import com.daowen.util.HTMLUtil;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;
import java.util.List;

public class XinxiVo extends Xinxi {

   private List<String> images;
   public  List<String> getImages(){
       if(getTupian()==null)
           return null;
      return Arrays.asList(getTupian().split("\\$;"));

   }
   private String lmname;

   private String subtypename;

    public String getSubtypename() {
        return subtypename;
    }

    public void setSubtypename(String subtypename) {
        this.subtypename = subtypename;
    }

    private String abstact;

   public String getAbstact(){
      if(getDcontent()==null||getDcontent().equals(""))
          return  "";
      return HTMLUtil.subStringInFilter(getDcontent(),0,300);
   }

    public String getLmname() {
        return lmname;
    }

    public void setLmname(String lmname) {
        this.lmname = lmname;
    }


    private String tagname;

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    private String pbrname;

    private String pbrphoto;

    public String getPbrphoto() {
        return pbrphoto;
    }

    public void setPbrphoto(String pbrphoto) {
        this.pbrphoto = pbrphoto;
    }

    public String getPbrname() {
        return pbrname;
    }

    public void setPbrname(String pbrname) {
        this.pbrname = pbrname;
    }
}
