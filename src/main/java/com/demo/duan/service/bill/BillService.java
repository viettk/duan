package com.demo.duan.service.bill;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BillService {
    public ResponseEntity<BillDto> createByCustomer(Integer cartId ,BillInput input);

    public ResponseEntity<BillDto> createByCustomerNotLogin(BillInput input );

    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input);

    public ResponseEntity<Page<BillDto>> getCustomerId(String email, Optional<Integer> page, Optional<Integer> limit);

    public ResponseEntity<List<BillDetailDto>> getBillDetailCustomer(Integer billId);

    public Integer getDonHuy (Integer month, Integer year);

    public Integer getDonTra (Integer month, Integer year);

    public Integer getDonTc (Integer month, Integer year);

    public Object sanPhambanchy(Integer month, Integer year);

    public List<BigDecimal> thongkedoanhthu(Integer year);

    //bill admin

    ResponseEntity<Page<BillDto>>getAll(Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

    ResponseEntity<BillDto>getOne(Integer id);

    ResponseEntity<BillDto>update(BillInput input, Integer id);

    ResponseEntity<Page<BillDto>>getByEmail(String name, Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

    ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input);

    ResponseEntity<BillDto> updateStatusPay(Integer id, BillInput input);

    ResponseEntity<Page<BillDto>>getByStatus(String pay, String order, Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);
}
