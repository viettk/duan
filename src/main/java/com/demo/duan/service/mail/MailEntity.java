package com.demo.duan.service.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailEntity {
    String from="dunglvph11016@fpt.edu.vn";
    String to;
    String[] cc;
    String[] bcc;
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

}
