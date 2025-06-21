package com.daowen.controller;

import com.alibaba.fastjson.JSONObject;
import com.daowen.entity.Leaveword;
import com.daowen.service.LeavewordService;
import com.daowen.util.JsonResult;
import com.daowen.vo.LeavewordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ServerEndpoint("/imchat")
@Component
public class WebSocketController {


    private static LeavewordService leavewordSrv=null;

    @Autowired
    public void setLeavewordSrv(LeavewordService leavewordSrv){
       WebSocketController.leavewordSrv=leavewordSrv;
    }
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("连接成功");
    }

    /**
     * 连接关闭
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("连接关闭");
    }

    /**
     * 接收到消息
     * @param text
     */
    @OnMessage
    public String onMsg(String text) throws IOException {
        JSONObject jo=JSONObject.parseObject(text);
        String dcontent=jo.getString("dcontent");
        String hyid=jo.getString("hyid");
        Leaveword leaveword = new Leaveword();
        leaveword.setDcontent(dcontent == null ? "" : dcontent);
        leaveword.setPubtime(new Date());
        leaveword.setState(2);
        leaveword.setHyid(hyid == null ? 0 : new Integer(hyid));
        leaveword.setReplytime(new Date());
        leaveword.setReplyren("admin");
        leaveword.setReplycontent("你好我是小智机器人，收到你的消息你耐心等待");
        leavewordSrv.save(leaveword);
        HashMap map = new HashMap();
        map.put("hyid",hyid);
        map.put("order","order by id asc");
        List<LeavewordVo> listLeaveword= leavewordSrv.getEntityPlus(map);
        return JSONObject.toJSONString(JsonResult.success(1,"",listLeaveword));
    }

}
