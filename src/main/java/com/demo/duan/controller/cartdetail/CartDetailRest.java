package com.demo.duan.controller.cartdetail;

import com.demo.duan.service.cartdetail.CartDetailService;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.input.CartDetailInput;
import com.demo.duan.service.cartdetail.input.CartDetalInputDelete;
import com.demo.duan.service.cartdetail.param.CartDetailParam;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart-detail")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CartDetailRest {
    private final CartDetailService service;

    @GetMapping
    public ResponseEntity<List<CartDetailDto>> find(CartDetailParam param){
        return service.find(param);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDetailDto> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<CartDetailDto> addToCart(@Valid @RequestBody CartDetailInput input){
        return service.addToCartDetail(input);
    }

    @PutMapping
    public ResponseEntity<CartDetailDto> updateNumber(@Valid @RequestBody CartDetailInput input){
        return service.updateNumber(input);
    }

    @PutMapping("/up")
    public ResponseEntity<CartDetailDto> updateNumberUp(@Valid @RequestBody CartDetailInput input){
        return service.updateNumberUp(input);
    }

    @PutMapping("/down")
    public ResponseEntity<CartDetailDto> updateNumberDown(@Valid @RequestBody CartDetailInput input){
        return service.updateNumberDown(input);
    }

    @DeleteMapping("")
    public ResponseEntity<CartDetailDto> delete(@Valid @RequestBody CartDetalInputDelete input){
        return service.delete(input);
    }

}
