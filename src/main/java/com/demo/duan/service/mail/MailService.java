package com.demo.duan.service.mail;

import javax.mail.MessagingException;

public interface MailService {
    public void send(MailEntity mail) throws MessagingException;
}
