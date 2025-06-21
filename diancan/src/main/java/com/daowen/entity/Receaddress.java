package com.daowen.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Receaddress
{
@Id
@GeneratedValue(strategy =GenerationType.AUTO)
   private int id ;
   public int getId() 
   {
      return id;
  }
   public void setId(int id) 
   {
      this.id= id;
  }
   private String title ;
   public String getTitle() 
   {
      return title;
  }
   public void setTitle(String title) 
   {
      this.title= title;
  }
   private String shr ;
   public String getShr() 
   {
      return shr;
  }
   public void setShr(String shr) 
   {
      this.shr= shr;
  }
   private String mobile ;
   public String getMobile() 
   {
      return mobile;
  }
   public void setMobile(String mobile) 
   {
      this.mobile= mobile;
  }
   private String postcode ;
   public String getPostcode() 
   {
      return postcode;
  }
   public void setPostcode(String postcode) 
   {
      this.postcode= postcode;
  }
   private String addinfo ;
   public String getAddinfo() 
   {
      return addinfo;
  }
   public void setAddinfo(String addinfo) 
   {
      this.addinfo= addinfo;
  }
   private int hyid;

    public int getHyid() {
        return hyid;
    }

    public void setHyid(int hyid) {
        this.hyid = hyid;
    }
}
