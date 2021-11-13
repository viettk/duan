package com.demo.duan.service.receiptDetail.dto;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.receipt.dto.ReceiptDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class ReceiptDetailDto {
    private Integer id;
    private ProductDto product;
    private ReceiptDto receipt;
    private Integer number;
    private BigDecimal price;
    private BigDecimal total;


}
