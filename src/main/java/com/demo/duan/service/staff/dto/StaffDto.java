package com.demo.duan.service.staff.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
public class StaffDto {
    private Integer id;

    private String email;

    private String token;

    private String name;

    private Integer role;

    private boolean status;

    private String phone;

}
