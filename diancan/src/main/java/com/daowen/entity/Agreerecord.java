package com.daowen.entity;
import javax.persistence.*;
@Entity
public class Agreerecord
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
   private String targetid ;
   public String getTargetid() 
   {
      return targetid;
  }
   public void setTargetid(String targetid) 
   {
      this.targetid= targetid;
  }
   private String commentator ;
   public String getCommentator() 
   {
      return commentator;
  }
   public void setCommentator(String commentator) 
   {
      this.commentator= commentator;
  }
   private String typename ;
   public String getTypename() 
   {
      return typename;
  }
   public void setTypename(String typename) 
   {
      this.typename= typename;
  }
}
