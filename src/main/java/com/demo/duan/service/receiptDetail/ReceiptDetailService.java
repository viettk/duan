package com.demo.duan.service.receiptDetail;


import com.demo.duan.service.category.param.CategoryParam;
import com.demo.duan.service.receipt.param.ReceiptParam;
import com.demo.duan.service.receiptDetail.dto.ReceiptDetailDto;
import com.demo.duan.service.receiptDetail.input.ReceiptDetailInput;
import com.demo.duan.service.receiptDetail.param.ReceiptDetailparam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ReceiptDetailService {
    ResponseEntity<Page<ReceiptDetailDto>> searchByAdmin(Integer receiptId, ReceiptDetailparam param, Optional<Integer> limit, Optional<Integer> page );
    ResponseEntity<ReceiptDetailDto> findByIdRecript (Integer id);
    ResponseEntity<ReceiptDetailDto> create (ReceiptDetailInput input);
    ResponseEntity<ReceiptDetailDto> update (Integer id, ReceiptDetailInput input);
    ResponseEntity<ReceiptDetailDto> deleteById(Integer id, Integer receiptId);

    ResponseEntity<ReceiptDetailDto> getId(Integer id);
}
