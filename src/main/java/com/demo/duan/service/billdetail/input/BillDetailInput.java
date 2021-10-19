package com.demo.duan.service.billdetail.input;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillDetailInput {
    private Integer productId;

    private Integer billId;

    /*Số lượng của 1 sản phẩm*/
    private Integer number;

    private BigDecimal price;
}
