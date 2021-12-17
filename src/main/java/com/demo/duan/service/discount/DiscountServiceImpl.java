package com.demo.duan.service.discount;

import com.demo.duan.entity.DiscountEntity;
import com.demo.duan.repository.discount.DiscountRepository;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.param.CategoryParam;
import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import com.demo.duan.service.discount.mapper.DiscountMapper;
import com.demo.duan.service.discount.param.DiscountParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService{
    private final DiscountMapper mapper;

    private final DiscountRepository repository;

    @Override
    @Transactional
    public ResponseEntity<Page<DiscountDto>> find(DiscountParam param,
                                                  Optional<String> field, Optional<String> known,
                                                  Optional<Integer> limit,
                                                  Optional<Integer> page ) {
        if(known.get().equals("up")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<DiscountDto> dto = this. repository.find(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        } else{
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<DiscountDto> dto = repository.find(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
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

        if(input.getOpen_day().compareTo(input.getEnd_day()) > 0  ){
            throw new RuntimeException("Ngày kết thúc phải lớn hơn ngày mở");
        }

        DiscountEntity entity = mapper.inputToEntity(input);
        repository.save(entity);
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
            throw new RuntimeException("Số lượng mã giảm giá phải >= 0");
        }

        if(input.getOpen_day().compareTo(input.getEnd_day()) > 0 ){
//            throw new RuntimeException("Ngày kết thúc phải lớn hơn hoặc bằng ngày mở");
            throw new RuntimeException("Ngày mở phải nhỏ hơn ngày kết thức");
        }

        mapper.inputToEntity(input, entity);
        repository.save(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public Integer apdung(String discountName) {
        DiscountEntity discount = repository.searchdiscount(discountName)
                .orElseThrow(()-> new RuntimeException("Mã Giảm giá không khả dụng"));
        return discount.getValueDiscount();
    }

    @Override
    @Transactional
    public ResponseEntity<DiscountDto> get(Integer id) {
        DiscountEntity discount = repository.getById(id);
        return ResponseEntity.ok().body(mapper.entityToDto(discount));
    }
}
