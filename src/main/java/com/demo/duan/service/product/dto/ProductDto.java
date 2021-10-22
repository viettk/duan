package com.demo.duan.service.product.dto;


import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.photo.dto.PhotoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductDto {
    private CategoryDto category;

    private String name;

    private boolean status;

    private BigDecimal price;

    private Float rating;

    private Date create_date;

    private Integer number;

    private String describe;

    private String photo;

    private List<PhotoDTO> photos;

}
