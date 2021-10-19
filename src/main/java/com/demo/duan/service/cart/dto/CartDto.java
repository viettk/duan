package com.demo.duan.service.cart.dto;

import com.demo.duan.entity.CartDetailEntity;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.service.customer.dto.CustomerDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private CustomerDto customer;

    private Date create_date;

    private BigDecimal total;

}
