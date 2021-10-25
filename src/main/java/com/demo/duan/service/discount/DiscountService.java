package com.demo.duan.service.discount;

import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import org.springframework.http.ResponseEntity;

public interface DiscountService {
//    public ResponseEntity<DiscountDto> find();

    public ResponseEntity<DiscountDto> create(DiscountInput input);

    public ResponseEntity<DiscountDto> update(Integer id, DiscountInput input);
}
