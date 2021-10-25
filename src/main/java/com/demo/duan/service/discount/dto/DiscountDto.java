package com.demo.duan.service.discount.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiscountDto {
    private String name;

    private int valueDiscount;

    private int number;

    private Date open_day;

    private Date end_day;
}
