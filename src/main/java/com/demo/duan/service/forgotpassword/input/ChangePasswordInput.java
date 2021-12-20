package com.demo.duan.service.forgotpassword.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ChangePasswordInput {
    private String password;
    @NotBlank(message = "Không được để trống password mới")
    private String newPassword;
    @NotBlank(message = "Không được để trống nhập lại password mới")
    private String repeatNewPassword;
    private String token;
}
