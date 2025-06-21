package com.daowen.entity;
import javax.persistence.*;
@Entity
public class Psitem
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
   private int lmid ;
   public int getLmid() 
   {
      return lmid;
  }
   public void setLmid(int lmid) 
   {
      this.lmid= lmid;
  }

   private int pageid ;
   public int getPageid() 
   {
      return pageid;
  }
   public void setPageid(int pageid) 
   {
      this.pageid= pageid;
  }
}
