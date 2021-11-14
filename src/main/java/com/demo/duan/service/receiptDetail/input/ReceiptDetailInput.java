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
    private Integer productId;
    private Integer receiptId;
    @NotNull(message = "Vui lòng điền số lượng nhập sản phẩm")
    @Min(value = 1 , message = "Số lượng nhập phải lớn hơn 0")
    private Integer number;
    @NotNull(message = "Mời bạn điền giá nhâp của sản phẩm")
    @Min(value = 1 , message = "Giá nhập phải lớn hơn 0" )
    private BigDecimal price;
}
