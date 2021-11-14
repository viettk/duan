package com.demo.duan.service.receipt.input;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter

public class ReceiptInput {
    @NotNull( message = "không được để trống")
    private Integer staffId;
    private BigDecimal total;
    private String describe;
    private String id_code;

}
