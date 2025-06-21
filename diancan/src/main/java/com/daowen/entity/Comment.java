package com.daowen.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	private int hyid;

	public int getHyid() {
		return hyid;
	}

	public void setHyid(int hyid) {
		this.hyid = hyid;
	}


	private Date createtime;

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	private int agreecount;

	public int getAgreecount() {
		return agreecount;
	}

	public void setAgreecount(int agreecount) {
		this.agreecount = agreecount;
	}

	private String  cdata;

	public String getCdata() {
		return cdata;
	}

	public void setCdata(String cdata) {
		this.cdata = cdata;
	}

	private String xtype;

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	private String belongid;

	public String getBelongid() {
		return belongid;
	}

	public void setBelongid(String belongid) {
		this.belongid = belongid;
	}
	
	private int topicid;
	public int getTopicid() {
		return topicid;
	}

	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}

	public int getIstopic() {
		return istopic;
	}

	public void setIstopic(int istopic) {
		this.istopic = istopic;
	}

	private int istopic;


	@Transient
	private String hyaccount;
	@Transient
	private String hyname;
	@Transient
	private String photo;

	public String getHyaccount() {
		return hyaccount;
	}

	public void setHyaccount(String hyaccount) {
		this.hyaccount = hyaccount;
	}

	public String getHyname() {
		return hyname;
	}

	public void setHyname(String hyname) {
		this.hyname = hyname;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
