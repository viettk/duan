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
    String status_order;
    String status_pay;
    Date date_start;
    Date date_end;
}
