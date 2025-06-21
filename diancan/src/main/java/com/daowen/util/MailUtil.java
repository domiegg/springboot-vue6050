package com.daowen.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailUtil {

    private  String sendAccount = "dl2020126@126.com";
    private  String sendAccountRemark="忘记密码";
    private  String authenPwd = "JMMKMYRYGGQJCTYE";

    private  Session  session=null;

    public static String smtpServer = "smtp.126.com";

    private MailUtil(){

    }

    public static void main(String[] args) throws Exception {
           MimeMessageDescription mmd=new MimeMessageDescription();
           mmd.setReceAccount("1156928778@qq.com");
           mmd.setReceAccountRemark("账户描述");
           mmd.setSubject("忘记密码");
           mmd.setContent("亲忘记密码<a href=\"http://localhost:8080/boyue/e/resetpw.jsp\">重置密码</a>");
           send(mmd);
    }

    public static boolean send(MimeMessageDescription mmd){
        MailUtil mailUtil=new MailUtil();
       return mailUtil.sendEmail(mmd);
    }

    private  boolean sendEmail(MimeMessageDescription mmd){
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", smtpServer);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(props);
        session.setDebug(true);
        boolean result=true;
        try {
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect(sendAccount, authenPwd);
            MimeMessage message = createMimeMessage(mmd);
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
        }catch (Exception e){
            result=false;
        }
        return result;

    }

    private  MimeMessage createMimeMessage(MimeMessageDescription mmd) throws Exception {
        if(session==null||mmd==null)
            return null;
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendAccount, sendAccountRemark, "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mmd.getReceAccount(), mmd.getReceAccountRemark(), "UTF-8"));
        message.setSubject(mmd.getSubject(), "UTF-8");
        message.setContent(mmd.getContent(), "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

}
