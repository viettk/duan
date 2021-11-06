package com.demo.duan.service.bill;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillService {
    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input);
    public ResponseEntity<List<BillDto>> getStatus();
}
