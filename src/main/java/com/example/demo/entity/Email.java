package com.example.demo.entity;

public class Email {

    private String msgBody;
    private String subject;

    public Email(String msgBody, String subject) {
        this.msgBody = msgBody;
        this.subject = subject;
    }

    public Email(){}


    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
