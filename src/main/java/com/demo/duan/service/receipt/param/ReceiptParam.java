package com.demo.duan.service.receipt.param;

import com.demo.duan.entity.StaffEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ReceiptParam {
    private Integer staffId;
    private BigDecimal total;
    private Date create_date;
    private String describe;
}
