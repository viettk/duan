package com.demo.duan.service.receipt.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.service.receipt.dto.ReceiptDto;
import com.demo.duan.service.receipt.input.ReceiptInput;
import jdk.jfr.Registered;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper (componentModel = "spring" , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface ReceiptMapper extends ParentMapper<ReceiptEntity , ReceiptDto , ReceiptInput> {
    @Override
    @Mapping(source = "staff.id" , target = "staffId")
    ReceiptDto entityToDto(ReceiptEntity entity);
}
