package com.demo.duan.service.billdetail;

import com.demo.duan.entity.LocalStorageBillDetail;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BillDetailService {
    public ResponseEntity<List<BillDetailDto>> createByCustomer(BillDetailInput input, Integer cartId);
    public ResponseEntity<List<BillDetailDto>> createByCustomerNotLogin(Integer id ,List<LocalStorageBillDetail> input);
    public BigDecimal totalOfBill(Integer billId);
     /*bill admin*/
    ResponseEntity<List<BillDetailDto>>getByBill(Integer idBill, Optional<String> field, String known);

    ResponseEntity<BillDetailDto>updateBillDetail(Integer id, BillDetailInput input);

    ResponseEntity<BillDetailDto>getById(Integer id);

    ResponseEntity<List<BillDetailDto>> getAllAfterOrder(Integer billId);

    public Float getAllWeight(Integer billId);

    public BigDecimal getTotalBillDetail(Integer billId);

    //admin
    ResponseEntity<List<BillDetailDto>>getByBill(Integer idBill, Optional<String> field, String known);

    ResponseEntity<BillDetailDto>updateBillDetail(Integer id, BillDetailInput input);

    ResponseEntity<BillDetailDto>getById(Integer id);
}
