package com.demo.duan.service.discount.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.DiscountEntity;
import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DiscountMapper extends ParentMapper<DiscountEntity, DiscountDto, DiscountInput> {
}
