package com.demo.duan.service.billreturn.dto;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.BillReturnEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BillReturnMapper extends ParentMapper <BillReturnEntity, BillReturnDto, BillReturnEntity>{
}
