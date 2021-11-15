package com.demo.duan.service.billdetail;

import com.demo.duan.entity.LocalStorageBillDetail;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface BillDetailService {
    public ResponseEntity<List<BillDetailDto>> createByCustomer(BillDetailInput input, Integer cartId);
    public ResponseEntity<List<BillDetailDto>> createByCustomerNotLogin(Integer id ,List<LocalStorageBillDetail> input);
    public BigDecimal totalOfBill(Integer billId);
}
