package com.demo.duan.service.staff.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
public class StaffDto {
    private Integer id;

    @Column(name="email")
    private String email;

    @Column(name="token")
    private String token;

    @Column(name="name")
    private String name;

    @Column(name="role")
    private String role;

    @Column(name="status")
    private boolean status;

    @Column(name="phone")
    private String phone;

}
