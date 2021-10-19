package com.demo.duan.service.product.dto;

import com.demo.duan.entity.PhotoEntity;
import com.demo.duan.service.category.dto.CategoryDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
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

    private Date crate_date;

    private Integer number;

    private String describe;

    private String photo;


//    private List<PhotoEntity> photos ;

}
