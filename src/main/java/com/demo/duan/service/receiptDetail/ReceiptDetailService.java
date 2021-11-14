package com.demo.duan.service.receiptDetail;


import com.demo.duan.service.receiptDetail.dto.ReceiptDetailDto;
import com.demo.duan.service.receiptDetail.input.ReceiptDetailInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ReceiptDetailService {
    ResponseEntity<Page<ReceiptDetailDto>> getAll(Pageable pageable);
    ResponseEntity<ReceiptDetailDto> findByIdRecript (Integer id);
    ResponseEntity<ReceiptDetailDto> create (ReceiptDetailInput input);
    ResponseEntity<ReceiptDetailDto> update (Integer id, ReceiptDetailInput input);
}
