package com.demo.duan.controller.login;

import com.demo.duan.service.forgotpassword.ForgotService;
import com.demo.duan.service.forgotpassword.input.ForgotPasswordInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("forgot")
@CrossOrigin("*")
@AllArgsConstructor
public class ForgotPasswordRest {
   private final ForgotService forgotService;

    @GetMapping()
    public ResponseEntity<Object> getTokenEmail(@RequestParam("email") Optional<String> email) throws MessagingException {
        return forgotService.getTokenEmail(email);
    }
    @GetMapping("/check/{token}")
    public ResponseEntity<Object> checkToken(@PathVariable("token") Optional<String> token){
        return forgotService.checkToken(token);
    }
    @PostMapping("/changepassword")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ForgotPasswordInput input){
        return forgotService.changePassword(input);
    }
}
