package com.demo.duan.service.cart.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.CartEntity;
import com.demo.duan.service.cart.dto.CartDto;
import com.demo.duan.service.cart.input.CartInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CartMapper extends ParentMapper<CartEntity, CartDto, CartInput> {
}
