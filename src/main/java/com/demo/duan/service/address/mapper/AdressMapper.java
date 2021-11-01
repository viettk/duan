package com.demo.duan.service.address.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.AdressEntity;
import com.demo.duan.service.address.dto.AdressDto;
import com.demo.duan.service.address.input.AdressInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AdressMapper extends ParentMapper<AdressEntity, AdressDto, AdressInput> {
}
