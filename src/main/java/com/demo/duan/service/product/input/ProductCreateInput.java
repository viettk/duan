package com.demo.duan.service.product.input;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class ProductCreateInput {
    @NotNull(message = "Không được để trống danh mục")
    private Integer categoryID;

    @NotBlank(message = "Không được để trống tên sản phẩm")
    private String name;

    @NotNull(message = "Không được để trống trạng thái")
    private boolean status;

    @NotNull(message = "Không được để trống số lượng sản phẩm")
    private Integer number;

    @Positive(message = "Giá sản phẩm phải > 0 ")
    private BigDecimal price;

    @NotNull(message = "Không được để trống miêu tả sản phẩm")
    private String describe;

    private Date createDate;

//    @NotEmpty(message = "Không được để trống ảnh chính sản phẩm")

    private String sku;

    private String trait;

    private Date releaseDate;

    private Integer price_release;

    private Float weight;

    private Float height;

    private Float width;

    private Float length;

}
