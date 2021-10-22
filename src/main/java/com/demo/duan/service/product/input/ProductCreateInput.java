package com.demo.duan.service.product.input;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ProductCreateInput {
    @NotNull(message = "Không được để trống danh mục")
    private Integer categoryID;

    @NotBlank(message = "Không được để trống tên sản phẩm")
    private String name;

    @NotNull(message = "Không được để trống trạng thái")
    private boolean status;

    @Positive(message = "Giá sản phẩm phải > 0 ")
    private BigDecimal price;

    private Date crate_date = new Date();

    @NotNull(message = "Không được để trống số lượng sản phẩm")
    private Integer number;

    @NotNull(message = "Không được để trống miêu tả sản phẩm")
    private String describe;

    @NotEmpty(message = "Không được để trống ảnh chính sản phẩm")
    private MultipartFile photo1;

    private MultipartFile photo2;

    private MultipartFile photo3;

    private MultipartFile photo4;

    private List<Integer> listTagID;

}
