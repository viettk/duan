package com.demo.duan.service.billdetail;

import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillDetailService {
    public ResponseEntity<List<BillDetailDto>> createByCustomer(BillDetailInput input, Integer cartId);
<<<<<<< HEAD

    ResponseEntity<List<BillDetailDto>>getByBill(Integer idBill, Optional<String> field, String known);

    ResponseEntity<BillDetailDto>updateBillDetail(Integer id, BillDetailInput input);
    public  ResponseEntity<BillDetailDto> update(BillDetailInput input , Integer id);
    public  void deleteById(Integer id);

=======
>>>>>>> parent of 11e5ca7 (bill mânger)
}
