package com.demo.duan.service.billdetail.dto;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.product.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillDetailDto {
    private ProductDto product;

    private BillDto bill;

    /*Số lượng của 1 sản phẩm*/
    private Integer number;

    private BigDecimal price;

    /*Thành tiền của tất cả sản phẩm trong phiếu nhập*/
    private BigDecimal total;
}
