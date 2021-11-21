package com.demo.duan.service.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerDto {
    private Integer id;
    private String email;

    private String name;

    private String token;

    private boolean status;

    private Date register_day;

    private Date last_login;
}
