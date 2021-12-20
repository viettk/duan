package com.demo.duan.service.customer.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
public class CustomerInput {

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+",message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Password không được để trống")
    private String password;

    @NotBlank(message = "Mời nhập lại mật khẩu")
    private String repeatPassword;

    @NotBlank(message = "Họ tên không được để trống")
    private String name;

//    private String token;

    private boolean status = true;

//    private Date register_day;
//
//    private Date last_login;
}
