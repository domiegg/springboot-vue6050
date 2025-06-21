package com.daowen.entity;
import javax.persistence.*;
@Entity
public class Lanmu
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
   private String name ;
   public String getName() 
   {
      return name;
  }
   public void setName(String name) 
   {
      this.name= name;
  }

    /**
     * 栏目类型
     * .1
     */
   private int type;
   public int getType(){
       return  type;
   }
   public void setType(int type){
       this.type=type;
   }
    private String bannerurl;
    public String getBannerurl() {
        return bannerurl;
    }
    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl;
    }
}
