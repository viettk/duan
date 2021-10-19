package com.demo.duan.service.billdetail;

import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillDetailService {
    public ResponseEntity<List<BillDetailDto>> createByCustomer(BillDetailInput input, Integer cartId);
}
