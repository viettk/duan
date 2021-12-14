package com.demo.duan.service.bill.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillParam {
    Integer status_order;
    Integer status_pay;
    Date date_start;
    Date date_end;
}
