package com.demo.duan.service.mail;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface MailService {
    public void send(MailEntity mail) throws MessagingException;

    public void sendAll(MultipartFile file,String title,String content) throws MessagingException;
}
