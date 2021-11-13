package com.demo.duan.service.receipt;

import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.service.receipt.dto.ReceiptDto;
import com.demo.duan.service.receipt.input.ReceiptInput;
import com.demo.duan.service.receipt.param.ReceiptParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;


import java.util.Date;
import java.util.Optional;

public interface ReceiptService {
    ResponseEntity<Page<ReceiptDto>> getAll(Integer month, Integer years, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);
    ResponseEntity<ReceiptDto> getOne(Integer id);
    ResponseEntity<ReceiptDto> create(ReceiptInput input);
    ResponseEntity<ReceiptDto> update(Integer id , ReceiptInput input );
    ResponseEntity<ReceiptDto> findByDate(Date date);
//    ResponseEntity <Page<ReceiptDto>> findDate(Date getStartOfDay ,Date getEndOfDay);
}
