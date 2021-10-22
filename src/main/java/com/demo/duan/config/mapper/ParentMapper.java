package com.demo.duan.config.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface ParentMapper<Entity, Dto, Input> {
    Entity inputToEntity(Input input);

    Dto entityToDto(Entity entity);

    void inputToEntity(Input input, @MappingTarget Entity entity);

    List<Dto> EntitiesToDtos(List<Entity> entities);
}
