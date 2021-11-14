package com.demo.duan.service.receiptDetail.input;

import ch.qos.logback.core.boolex.EvaluationException;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.entity.ReceiptEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter

public class ReceiptDetailInput {

    @NotNull(message = "Sản phẩm không được để trống")
    private Integer productId;

    private Integer receiptId;

    @NotNull(message = "Số lượng nhập không được để trống")
    @Min(value = 1 , message = "Số lượng nhập phải lớn hơn 0")
    private Integer number;

    @NotNull(message = "Giá nhập không được để trống")
    @Min(value = 1 , message = "Giá nhập phải lớn hơn 0" )
    private BigDecimal price;
}
