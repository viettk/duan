package com.demo.duan.service.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailEntity {
    private String from="dunglvph11016@fpt.edu.vn";
    private String to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String body;
    private String[] attachments;
    private String token;
    private Integer billID;
    public MailEntity(String to, String subject, String body,String token){
        this.to = to;
        this.subject = subject;
        this.body = body;
            this.token = token;
    }
    public MailEntity(String to, String subject,List<String> bcc){
        this.to = to;
        this.subject = subject;
        this.bcc = bcc;
    }
    public MailEntity(String to, String subject, String body,Integer billID){
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.billID = billID;
    }


}
