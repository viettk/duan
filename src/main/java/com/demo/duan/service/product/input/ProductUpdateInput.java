package com.demo.duan.service.product.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class ProductUpdateInput {
    private Integer id;
    @NotNull(message = "Không được để trống danh mục")
    private Integer categoryID;

    @NotBlank(message = "Không được để trống tên sản phẩm")
    private String name;

    private boolean status;

    @NotNull(message = "Không được để trống giá bán")
    @Positive(message = "Giá sản phẩm phải lớn hơn 0 ")
    private BigDecimal price_extra;

    @NotBlank(message = "Không được để trống miêu tả sản phẩm")
    private String describe;

//    @NotEmpty(message = "Không được để trống ảnh chính sản phẩm")

    private String trait;

    private LocalDate releaseDate;

    @Positive(message = "Giá niêm yết phải lớn hơn 0")
    private Integer price_release;

    @Positive(message = "cân nặng phải lớn hơn 0 ")
    private Float weight;

    @Positive(message = "chiều cao phải lớn hơn 0 ")
    private Float height;

    @Positive(message = "Chiều rộng phải lớn hơn 0 ")
    private Float width;

    @Positive(message = "Chiều dài phải lớn hơn 0 ")
    private Float length;
}
