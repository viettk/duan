package com.demo.duan.service.product.dto;

import com.demo.duan.entity.PhotoEntity;
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
    private Integer id;

    private CategoryDto category;

    private String name;

    private boolean status;

    private BigDecimal price;

    private Date createDate;

    private Integer number;

    private String describe;

    private String photo;

    private BigDecimal price_extra;

    private Integer value_extra ;

    private List<PhotoDTO> photos;



    //    private List<PhotoEntity> photos ;
    private String sku;

    /* Đặc điểm nổi bật */

    private String trait;

    private Date releaseDate;

    private BigDecimal price_release;

    private Float weight;

    private Float height;

    private Float width;

    private Float length;

}
