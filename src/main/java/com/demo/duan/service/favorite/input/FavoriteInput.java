package com.demo.duan.service.favorite.input;

import com.demo.duan.service.product.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteInput {
    private Integer productId;

    private Integer customerId;
}
