package com.demo.duan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalStorageBillDetail {
    private Integer product_id;
    private Integer number;
    private BigDecimal price;
    private BigDecimal total;
}
