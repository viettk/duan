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
    String from="dunglvph11016@fpt.edu.vn";
    String to;
    List<String> cc;
    List<String> bcc;
    String subject;
    String body;
    String[] attachments;
    String token;
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

}
