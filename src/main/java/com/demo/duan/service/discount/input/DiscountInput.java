package com.demo.duan.service.discount.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class DiscountInput {

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotNull(message = "Giá trị không được để trống")
    private int valueDiscount;

//    @NotNull(message = "Số lượng không được để trống")
    private int number;


    private LocalDate open_day;

    private LocalDate end_day;
}
