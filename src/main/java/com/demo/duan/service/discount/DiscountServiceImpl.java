package com.demo.duan.service.discount;

import com.demo.duan.entity.DiscountEntity;
import com.demo.duan.repository.discount.DiscountRepository;
import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import com.demo.duan.service.discount.mapper.DiscountMapper;
import com.demo.duan.service.discount.param.DiscountParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService{
    private final DiscountMapper mapper;

    private final DiscountRepository repository;

    @Override
    @Transactional
    public ResponseEntity<Page<DiscountDto>> find(DiscountParam param, Pageable pageable) {
        Page<DiscountDto> lstdtos = repository.find(param, pageable).map(mapper::entityToDto);
        return ResponseEntity.ok().body(lstdtos);
    }

    @Override
    @Transactional
    public ResponseEntity<DiscountDto> create(DiscountInput input) {
        /* Kiểm tra mã đã tồn tại hay chưa */
        long count = repository.countAllByName(input.getName());

        if(count > 0){
            throw new RuntimeException("Mã giảm giá đã tồn tại");
        }

        if(input.getNumber() < 0){
            throw new RuntimeException("Số lượng mã giảm giá phải >=0");
        }

        DiscountEntity entity = mapper.inputToEntity(input);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<DiscountDto> update(Integer id, DiscountInput input) {
        /* Lấy mã */
        DiscountEntity entity = repository.getById(id);

        long count = repository.countAllByName(input.getName());

        if(count > 1){
            throw new RuntimeException("Mã giảm giá đã tồn tại");
        }

        if(input.getNumber() < 0){
            throw new RuntimeException("Số lượng mã giảm giá phải >=0");
        }

        mapper.inputToEntity(input, entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public Integer apdung(String discountName) {
        DiscountEntity discount = repository.searchDiscountByCustomer(discountName)
                .orElseThrow(()->new RuntimeException("Mã Giảm giá không khả dụng"));
        return discount.getValueDiscount();
    }
}
