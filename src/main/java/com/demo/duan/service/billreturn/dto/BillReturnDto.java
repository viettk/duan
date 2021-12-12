package com.demo.duan.service.billreturn.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BillReturnDto {
    private Integer id;

    private String email;

    private Date create_date;

    private Date confirm_date;

    private String name;

    private String phone;

    private BigDecimal total;

    private String address;

    private String city;

    private String district;

    private String status;

    private String describe;

    private String id_code;
}
