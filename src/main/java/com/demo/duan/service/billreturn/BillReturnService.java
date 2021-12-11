package com.demo.duan.service.billreturn;

import com.demo.duan.entity.BillReturnDetailEntity;
import com.demo.duan.entity.BillReturnEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillReturnService {
    ResponseEntity<Page<BillReturnEntity>>findAll(Pageable pageable);
    ResponseEntity<Page<BillReturnEntity>>findAll(String status, Pageable pageable);
    BillReturnEntity returnBill(BillReturnEntity billReturn);
    BillReturnEntity updateBillReturn(Integer id, BillReturnEntity billReturn);
    BillReturnEntity confirmBillReturn(Integer id);
    BillReturnEntity undoBillReturn(Integer id);
    BillReturnEntity totalBillReturn(Integer id);

    List<BillReturnDetailEntity>getDetail(Integer id);
    BillReturnDetailEntity returnDetail(BillReturnDetailEntity billReturnDetail);
    BillReturnDetailEntity returnDetailConfirm(Integer id, BillReturnDetailEntity billReturnDetail);
}
