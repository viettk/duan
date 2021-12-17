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
public class ProductCreateInput {
    @NotNull(message = "Không được để trống danh mục")
    private Integer categoryID;

    @NotBlank(message = "Không được để trống tên sản phẩm")
    private String name;

    private boolean status;

    @Positive(message = "số lượng phải lớn hơn 0 ")
    @NotNull(message = "Số lượng không được để trống")
    private Integer number;

    @NotNull(message = "Không được để trống giá bán")
    @Positive(message = "Giá bán phải lớn hơn 0 ")
    private BigDecimal price_extra;

    @NotBlank(message = "Không được để trống miêu tả sản phẩm")
    private String describe;

    private LocalDate createDate = LocalDate.now();

//    @NotEmpty(message = "Không được để trống ảnh chính sản phẩm")

    private String trait;

    private LocalDate releaseDate;

    @NotNull(message = "Không được để trống giá nhập")
    @Positive(message = "Giá nhập phải lớn hơn 0")
    private BigDecimal price_release;

    @Positive(message = "cân nặng phải lớn hơn 0 ")
    private Float weight;

    @Positive(message = "chiều cao phải lớn hơn 0 ")
    private Float height;

    @Positive(message = "Chiều rộng phải lớn hơn 0 ")
    private Float width;

    @Positive(message = "Chiều dài phải lớn hơn 0 ")
    private Float length;

}
