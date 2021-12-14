package com.demo.duan.service.favorite.mapper;

import com.demo.duan.config.mapper.ParentMapper;
import com.demo.duan.entity.FavoriteEntity;
import com.demo.duan.service.favorite.dto.FavoriteDto;
import com.demo.duan.service.favorite.input.FavoriteInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FavoriteMapper  extends ParentMapper<FavoriteEntity, FavoriteDto, FavoriteInput> {
}
