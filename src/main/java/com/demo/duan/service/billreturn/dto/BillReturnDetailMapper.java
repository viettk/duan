package com.demo.duan.service.billreturn.dto;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.BillReturnDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BillReturnDetailMapper extends ParentMapper<BillReturnDetailEntity, BillReturnDetailDto, BillReturnDetailEntity> {
}
