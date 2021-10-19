package com.demo.duan.service.cartdetail.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.CartDetailEntity;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.input.CartDetailInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CartDetailMapper extends ParentMapper<CartDetailEntity, CartDetailDto, CartDetailInput> {
}
