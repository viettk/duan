package com.demo.duan.service.photo.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.PhotoEntity;
import com.demo.duan.service.photo.dto.PhotoDTO;
import com.demo.duan.service.photo.input.PhotoInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PhotoMapper extends ParentMapper<PhotoEntity, PhotoDTO, PhotoInput> {
}
