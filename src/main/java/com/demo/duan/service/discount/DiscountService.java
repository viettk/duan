package com.demo.duan.service.discount;

import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import com.demo.duan.service.discount.param.DiscountParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface DiscountService {
    public ResponseEntity<Page<DiscountDto>> find(DiscountParam param, Optional<Integer> limit,
                                                  Optional<Integer> page);

    public ResponseEntity<DiscountDto> create(DiscountInput input);

    public ResponseEntity<DiscountDto> update(Integer id, DiscountInput input);

    public Integer apdung(String discountName);

    public ResponseEntity<DiscountDto> get(Integer id);

}
