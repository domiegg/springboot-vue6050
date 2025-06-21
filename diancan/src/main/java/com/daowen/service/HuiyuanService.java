package com.daowen.service;

import com.daowen.entity.Huiyuan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daowen.mapper.HuiyuanMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("huiyuanService")
public class HuiyuanService extends SimpleBizservice<HuiyuanMapper> {


    @Autowired
    private HuiyuanMapper huiyuanMapper;
//从会员的余额中扣除指定的费用
    public int  deduct(int  hyId,double fee){
        if(hyId<=0)
            return -1;
        Huiyuan huiyuan=load("where id="+hyId);
        if(huiyuan==null)
            return -2;
        if(huiyuan.getYue()<fee)
            return -3;
        int count=this.executeUpdate(MessageFormat.format(" update huiyuan set yue=yue-{0,number,#} where id={1,number,#} ",fee,hyId));
        return count;

    }


//向会员的余额中存入指定的费用
    public int  deposit(int  hyId,double fee){
        if(hyId<=0)
            return -1;
        Huiyuan huiyuan=load("where id="+hyId);
        if(huiyuan==null)
            return -2;
        if(huiyuan.getYue()<fee)
            return -3;
        int count=this.executeUpdate(MessageFormat.format(" update huiyuan set yue=yue+{0,number,#} where id={1,number,#} ",fee,hyId));
        return count;

    }



//会员支付指定的费用。该方法接收三个参数：fee表示支付的费用金额，accountName表示支付账号，payPwd表示支付密码。
    public int pay(double fee, String accountName,String payPwd) {
        if(accountName==null||accountName.equals(""))
            return -1;
        Huiyuan huiyuan=load(MessageFormat.format("where accountname=''{0}''",accountName));
        if(huiyuan==null)
            return -1;
        if(!payPwd.equals(huiyuan.getPaypwd()))
            return -2;
        if(huiyuan.getYue()<fee)
            return -3;
        int count=executeUpdate(MessageFormat.format("update huiyuan set yue=yue-{0,number,#} where accountname=''{1}''",fee,accountName));
        return count>0?1:0;
    }

    public List<Huiyuan> getEntityPlus(Map map){

        return huiyuanMapper.getEntityPlus(map);
    }

    public Huiyuan loadPlus(Map map){

        return huiyuanMapper.loadPlus(map);
    }

    public Huiyuan  loadPlus(int id){
        HashMap map=new HashMap();
        map.put("id",id);
        return loadPlus(map);
    }


}
