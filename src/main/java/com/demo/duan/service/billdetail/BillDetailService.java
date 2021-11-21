package com.demo.duan.service.billdetail;

import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BillDetailService {
    public ResponseEntity<List<BillDetailDto>> createByCustomer(BillDetailInput input, Integer cartId);
    public  ResponseEntity<BillDetailDto> update(BillDetailInput input , Integer id);
    public  void deleteById(Integer id);


    ResponseEntity<List<BillDetailDto>>getByBill(Integer idBill, Optional<String> field, String known);

    ResponseEntity<BillDetailDto>updateBillDetail(Integer id, BillDetailInput input);
}
