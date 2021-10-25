package com.demo.duan.service.receipt.dto;

import com.demo.duan.entity.StaffEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class ReceiptDto {
    private Integer staffId;
    private BigDecimal total;
    private Date create_date;
    private String describe;
    private String id_code;
}
