package com.demo.duan.controller.login;

import com.demo.duan.service.forgotpassword.ForgotService;
import com.demo.duan.service.forgotpassword.input.ChangePasswordInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@AllArgsConstructor
@RestController
@RequestMapping("api/change-password")
@CrossOrigin(origins = "*")
public class ChangePasswordRest {
    private final ForgotService forgotService;
    @PostMapping
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordInput input){
        return forgotService.changePassword(input);
    }

}
