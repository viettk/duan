package com.demo.duan.service.bill;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.param.BillParam;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillService {
    public ResponseEntity<BillDto> createByCustomer(Integer cartId ,BillInput input);

    public ResponseEntity<BillDto> createByCustomerNotLogin(BillInput input );

    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input);

    public ResponseEntity<Page<BillDto>> getCustomerId(String email, BillParam param ,Optional<Integer> page, Optional<Integer> limit);

    public ResponseEntity<List<BillDetailDto>> getBillDetailCustomer(Integer billId);

    public Integer getDonHuy (Integer month, Integer year);

    public Integer getDonTra (Integer month, Integer year);

    public Integer getDonTc (Integer month, Integer year);

    public Integer getCOD();

    public Integer getVNPAY();

    public Integer getChoxacnhan();

    public Integer getDangChuanbi();

    public Integer getdangGiao();

    public Integer tuchoi();

    public Integer thanhcong();

    public Integer thatbai();

    public Object sanPhambanchy(Integer month, Integer year);

    public List<BigDecimal> thongkedoanhthu(Integer year);

    //bill admin

    ResponseEntity<BillDto>getOne(Integer id);

    ResponseEntity<Page<BillDto>>getByEmail(String email, BillParam param, Pageable pageable);

    ResponseEntity<BillDto>update(BillInput input, Integer id);

    ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input);

    ResponseEntity<BillDto> updateStatusPay(Integer id, BillInput input);

    ResponseEntity<Page<BillDto>> filterBill(BillParam param, Pageable pageable);

    public void CreateBillPdf(Integer billId , String name, String email, String phone, LocalDate date, BigDecimal totalBillMoney, String statusPay);

    public ResponseEntity<BillDto> changeStatus_pay(Integer id, String status_pay);
}
