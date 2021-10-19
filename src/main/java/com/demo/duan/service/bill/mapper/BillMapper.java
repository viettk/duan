package com.demo.duan.service.bill.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.BillEntity;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BillMapper extends ParentMapper<BillEntity, BillDto, BillInput> {
}
