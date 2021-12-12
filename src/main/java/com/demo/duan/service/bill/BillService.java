package com.demo.duan.service.bill;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.param.BillParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BillService {
    public ResponseEntity<BillDto> createByCustomer(BillInput input);

    ResponseEntity<BillDto>getOne(Integer id);

    ResponseEntity<Page<BillDto>>getByEmail(String email, BillParam param, Pageable pageable);

    ResponseEntity<BillDto>update(BillInput input, Integer id);
    
    ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input);
    
    ResponseEntity<BillDto> updateStatusPay(Integer id, BillInput input);

    ResponseEntity<Page<BillDto>> filterBill(BillParam param, Pageable pageable);

}
