package com.demo.duan.service.receiptDetail.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.ReceiptDetailEntity;
import com.demo.duan.service.receiptDetail.dto.ReceiptDetailDto;
import com.demo.duan.service.receiptDetail.input.ReceiptDetailInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper( componentModel = "spring"  , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ReceiptDetailMapper extends ParentMapper<ReceiptDetailEntity , ReceiptDetailDto , ReceiptDetailInput> {

}
