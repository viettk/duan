package com.demo.duan.service.bill;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {
    public ResponseEntity<BillDto> createByCustomer(Integer cartId ,BillInput input);

    public ResponseEntity<BillDto> createByCustomerNotLogin(BillInput input );

    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input);
}
