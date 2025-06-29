package com.daowen.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name="huiyuan")
public class Huiyuan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String accountname;

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	private Date regdate;

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	private int logtimes;

	public int getLogtimes() {
		return logtimes;
	}

	public void setLogtimes(int logtimes) {
		this.logtimes = logtimes;
	}

	private String touxiang;

	public String getTouxiang() {
		return touxiang;
	}

	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	private String sex;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private double yue;

	public double getYue() {
		return yue;
	}

	public void setYue(Double yue) {
		this.yue = yue;
	}
	
	private  String   idcardno;

	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
		
	}
	private String  des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
	private String paypwd;

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}


	private int typeid;

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	private String fans;

	public String getFans() {
		return fans;
	}

	public void setFans(String fans) {
		this.fans = fans;
	}


	private int fanscount;

	public int getFanscount() {
		return fanscount;
	}

	public void setFanscount(int fanscount) {
		this.fanscount = fanscount;
	}

	private int jifen;

	public int getJifen() {
		return jifen;
	}

	public void setJifen(int jifen) {
		this.jifen = jifen;
	}

	@Transient
	private Hytype hytype;

	public Hytype getHytype() {
		return hytype;
	}

	public void setHytype(Hytype hytype) {
		this.hytype = hytype;
	}

	@Transient
	private List<Receaddress> receaddresses;

	public List<Receaddress> getReceaddresses() {
		return receaddresses;
	}

	public void setReceaddresses(List<Receaddress> receaddresses) {
		this.receaddresses = receaddresses;
	}
}
