package com.demo.duan.service.bill.dto;

import com.demo.duan.service.discount.dto.DiscountDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BillDto {

    private Integer id;

    private String email;

    private Date create_date;

    private Date update_date;

    private String name;

    private String phone;

    private BigDecimal total;

    private Integer status_pay;

    private String address;

    private String city;

    private String district;

    private DiscountDto discount;

    private String wards;

    private Integer status_order;

    private String describe;

    private String thema;

    private String themb;

    private String themc;

    private String id_code;
}
