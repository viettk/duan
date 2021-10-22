package com.demo.duan.service.customer.param;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CustomerMapper extends ParentMapper<CustomerEntity, CustomerDto, CustomerInput> {
}
