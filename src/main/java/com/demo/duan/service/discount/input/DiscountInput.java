package com.demo.duan.service.discount.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiscountInput {
    private String name;

    private int valueDiscount;

    private int number;

    private LocalDate open_day;

    private LocalDate end_day;
}
