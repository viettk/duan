package com.demo.duan.service.billdetail.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BillDetailMapper extends ParentMapper<BillDetailEntity, BillDetailDto, BillDetailInput> {
}
