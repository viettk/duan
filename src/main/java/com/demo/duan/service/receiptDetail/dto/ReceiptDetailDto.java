package com.demo.duan.service.receiptDetail.dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class ReceiptDetailDto {
    private Integer id;
    private Integer productId;
    private Integer receiptId;
    private Integer number;
    private BigDecimal price;
    private BigDecimal total;


}
