package com.demo.duan.service.discount;

import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import com.demo.duan.service.discount.param.DiscountParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DiscountService {
    public ResponseEntity<Page<DiscountDto>> find(DiscountParam param, Pageable pageable);

    public ResponseEntity<DiscountDto> create(DiscountInput input);

    public ResponseEntity<DiscountDto> update(Integer id, DiscountInput input);
}
