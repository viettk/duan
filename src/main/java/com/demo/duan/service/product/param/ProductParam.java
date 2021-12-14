package com.demo.duan.service.product.param;

import com.demo.duan.service.category.dto.CategoryDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductParam {
    private String categoryName;

    private Integer categoryId;

    private String name;

    private BigDecimal price;

    private Boolean status;

    private Date crate_date;
}
