package com.demo.duan.service.cartdetail.input;

import com.demo.duan.entity.CartEntity;
import com.demo.duan.entity.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartDetailInput {
    private Integer cartId;

    private Integer productId;

    @NotNull(message = "Mời bạn nhập số lượng")
    @Min(value = 1, message = "Số lượng muốn thêm vào giỏ phải > 0")
    private Integer number;

}
