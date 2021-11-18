package com.demo.duan.service.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BillService {
    public ResponseEntity<BillDto> createByCustomer(BillInput input);

    ResponseEntity<Page<BillDto>>getAll(Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

    ResponseEntity<BillDto>getOne(Integer id);

    ResponseEntity<Page<BillDto>>getByEmail(String name, Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

    ResponseEntity<BillDto>update(BillInput input, Integer id);

    ResponseEntity<List<BillEntity>> demo();
    
    ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input);
    
    ResponseEntity<BillDto> updateStatusPay(Integer id);

    ResponseEntity<Page<BillDto>>getDone(Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

}
