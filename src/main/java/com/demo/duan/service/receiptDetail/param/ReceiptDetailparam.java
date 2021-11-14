package com.demo.duan.service.receiptDetail.param;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReceiptDetailparam {
    private String productName;
    private BigDecimal price;
    private String sku;
}
