package com.demo.duan.service.forgotpassword;

import com.demo.duan.service.forgotpassword.input.ForgotPasswordInput;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.Optional;

public interface ForgotService {
    ResponseEntity<Object> getTokenEmail(Optional<String> email) throws MessagingException;
    ResponseEntity<Object> checkToken(Optional<String> token);
    ResponseEntity<Object> changePassword(ForgotPasswordInput input);
}
