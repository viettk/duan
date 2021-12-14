package com.demo.duan.service.billreturn;

import com.demo.duan.entity.BillReturnDetailEntity;
import com.demo.duan.entity.BillReturnEntity;
import com.demo.duan.service.billreturn.dto.BillReturnDetailDto;
import com.demo.duan.service.billreturn.dto.BillReturnDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillReturnService {
    ResponseEntity<Page<BillReturnDto>>findAll(Pageable pageable);
    ResponseEntity<Page<BillReturnDto>>findAll(String status, Pageable pageable);
    BillReturnDto returnBill(BillReturnEntity billReturn);
    BillReturnDto updateBillReturn(Integer id, BillReturnEntity billReturn);
    BillReturnDto confirmBillReturn(Integer id);
    BillReturnDto undoBillReturn(Integer id);
    BillReturnDto totalBillReturn(Integer id);

    List<BillReturnDetailDto>getDetail(Integer id);
    BillReturnDetailDto returnDetail(BillReturnDetailEntity billReturnDetail);
    BillReturnDetailDto returnDetailConfirm(Integer id, BillReturnDetailEntity billReturnDetail);
}
