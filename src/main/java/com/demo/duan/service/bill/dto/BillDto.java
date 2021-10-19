package com.demo.duan.service.bill.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BillDto {
    private String email;

    private Date create_date;

    private Date update_date;

    private String name;

    private String phone;

    private BigDecimal total;

    private boolean status_pay;

    private String address;

    private String city;

    private String district;

    private String status_order;

    private String describe;

    private String thema;

    private String themb;

    private String themc;
}
