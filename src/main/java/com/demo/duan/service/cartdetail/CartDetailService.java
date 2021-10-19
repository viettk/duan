package com.demo.duan.service.cartdetail;

import com.demo.duan.entity.ProductEntity;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.input.CartDetailInput;
import com.demo.duan.service.cartdetail.param.CartDetailParam;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface CartDetailService {
    public ResponseEntity<List<CartDetailDto>> find(CartDetailParam param);

    public ResponseEntity<CartDetailDto> addToCartDetail(CartDetailInput input);

    /* tăng số lượng sp  */
    public ResponseEntity<CartDetailDto> updateNumberUp(Integer id);

    /* Giảm số lượng sp */
    public ResponseEntity<CartDetailDto> updateNumberDown(Integer id);

    public ResponseEntity<CartDetailDto> updateNumber(CartDetailInput input);

   /* xóa sản phẩm trong giỏ hàng */
    public ResponseEntity<CartDetailDto> delete(Integer id);

    /* Tính thành tiền của Giỏ hàng - Cart */
    public BigDecimal totalOfCart(Integer cartId);

    /* Kiểm tra số lượng sp trong giỏ hàng */
    Integer numberOfCartDetail(Integer cartId);

}
