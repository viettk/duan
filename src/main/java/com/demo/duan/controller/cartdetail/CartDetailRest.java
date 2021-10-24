package com.demo.duan.controller.cartdetail;

import com.demo.duan.service.cartdetail.CartDetailService;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.input.CartDetailInput;
import com.demo.duan.service.cartdetail.param.CartDetailParam;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart-detail")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class CartDetailRest {
    private final CartDetailService service;

    @GetMapping
    public ResponseEntity<List<CartDetailDto>> find(CartDetailParam param){
        return service.find(param);
    }

    @PostMapping
    public ResponseEntity<CartDetailDto> addToCart(@Valid @RequestBody CartDetailInput input){
        return service.addToCartDetail(input);
    }

    @PutMapping
    public ResponseEntity<CartDetailDto> updateNumber(@Valid @RequestBody CartDetailInput input){
        return service.updateNumber(input);
    }

    @PutMapping("/up/{id}")
    public ResponseEntity<CartDetailDto> updateNumberUp(@Valid @RequestBody CartDetailInput input){
        return service.updateNumberUp(input);
    }

    @PutMapping("/down/{id}")
    public ResponseEntity<CartDetailDto> updateNumberDown(@Valid @RequestBody CartDetailInput input){
        return service.updateNumberDown(input);
    }

    @DeleteMapping("")
    public ResponseEntity<CartDetailDto> delete(
            @RequestParam(name = "cartId", required = false) Integer cartId,
            @RequestParam(name = "productId", required = false) Integer productId){
        return service.delete(cartId, productId);
    }

}
