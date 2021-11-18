package com.demo.duan.service.forgotpassword.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ForgotPasswordInput {
    @NotBlank(message = "Không được để trống password")
    private String password;
    @NotBlank(message = "Không được để trống nhập lại password")
    private String repeatPassword;
    private String token;
}
