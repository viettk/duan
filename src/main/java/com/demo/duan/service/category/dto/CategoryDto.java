package com.demo.duan.service.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    private String name;
    private String parent_name;
}
