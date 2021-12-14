package com.demo.duan.service.category.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CategoryInput {

    @NotBlank(message = "Danh mục không được trống")
    private String name;

    @NotBlank(message = "Danh mục cha không được trống")
    private String parent_name;
}
