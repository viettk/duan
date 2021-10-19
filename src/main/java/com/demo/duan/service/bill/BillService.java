package com.demo.duan.service.bill;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import org.springframework.http.ResponseEntity;

public interface BillService {
    public ResponseEntity<BillDto> createByCustomer(BillInput input);
}
