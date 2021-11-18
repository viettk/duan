package com.demo.duan.service.staff.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StaffInput {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Password không được để trống")
    private String password;

    private String token;

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Role không được để trống")
    private Integer role;

    private boolean status;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

}
