package com.demo.duan.service.discount.input;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiscountInput {
    private String name;

    private int valueDiscount;

    private int number;

    private Date open_day;

    private Date end_day;
}
