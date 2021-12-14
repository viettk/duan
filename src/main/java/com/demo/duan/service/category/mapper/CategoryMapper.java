package com.demo.duan.service.category.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.CategoryEntity;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryMapper extends ParentMapper<CategoryEntity, CategoryDto, CategoryInput> {
}
