package com.demo.duan.service.bill.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillParam {

    Integer status_order;
    Integer status_pay;
    LocalDate date_start;
    LocalDate date_end;
    String p;
}
