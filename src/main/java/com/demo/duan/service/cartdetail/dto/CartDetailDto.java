package com.demo.duan.service.cartdetail.dto;

import com.demo.duan.entity.CartEntity;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.service.cart.dto.CartDto;
import com.demo.duan.service.product.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartDetailDto {
    private ProductDto product;

    private CartDto cart;

    private Integer number;

    /*Thành tiền của tất cả sản phẩm trong phiếu nhập*/
    private BigDecimal total;
}
