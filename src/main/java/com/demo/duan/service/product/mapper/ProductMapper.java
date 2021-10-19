package com.demo.duan.service.product.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.service.product.param.ProductParam;
import com.demo.duan.service.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper extends ParentMapper<ProductEntity, ProductDto, ProductParam> {
}
