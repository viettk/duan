package com.demo.duan.service.staff.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface StaffMapper extends ParentMapper<StaffEntity, StaffDto, StaffInput> {
}
