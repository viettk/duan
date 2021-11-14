package com.demo.duan.service.receipt;

import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.service.receipt.dto.ReceiptDto;
import com.demo.duan.service.receipt.input.ReceiptInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;


import java.util.Date;

public interface ReceiptService {
    ResponseEntity<Page<ReceiptDto>> getAll(Pageable pageable);
    ResponseEntity<ReceiptDto> getOne(Integer id);
    ResponseEntity<ReceiptDto> create(ReceiptInput input);
    ResponseEntity<ReceiptDto> update(Integer id , ReceiptInput input );
    ResponseEntity<ReceiptDto> findByDate(Date date);
//    ResponseEntity <Page<ReceiptDto>> findDate(Date getStartOfDay ,Date getEndOfDay);
}
