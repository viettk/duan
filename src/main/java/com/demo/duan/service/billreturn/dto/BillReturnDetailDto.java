package com.demo.duan.service.billreturn.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillReturnDetailDto {

    private Integer id;

    private Integer number;

    private Integer real_number;

    private BigDecimal price;

    private BigDecimal total;
}
