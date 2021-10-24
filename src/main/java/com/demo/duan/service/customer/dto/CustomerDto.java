package com.demo.duan.service.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerDto {
    private String email;

    private String name;

    private String token;

    private boolean status;

    private Date last_login;
}
