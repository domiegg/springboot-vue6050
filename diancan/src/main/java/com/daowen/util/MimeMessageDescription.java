package com.daowen.util;

public class MimeMessageDescription {

    /**
     * 邮件主题
     */
    private  String  subject;

    /**
     * 接收人
     */
    private String receAccount;
    /**
     * 接收人描述
     */
    private String  receAccountRemark;
    /**
     * 邮件内容
     */
    private String  content;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceAccount() {
        return receAccount;
    }

    public void setReceAccount(String receAccount) {
        this.receAccount = receAccount;
    }

    public String getReceAccountRemark() {
        return receAccountRemark;
    }

    public void setReceAccountRemark(String receAccountRemark) {
        this.receAccountRemark = receAccountRemark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
