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

    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<CartDetailDto>> find(@PathVariable Integer customerId, String email){
        return service.find(customerId, email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDetailDto> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<CartDetailDto> addToCart(@PathVariable Integer customerId, String email,@Valid @RequestBody CartDetailInput input){
        return service.addToCartDetail(customerId, email,input);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CartDetailDto> updateNumber(
            @PathVariable Integer customerId,
            String email,
            @Valid @RequestBody CartDetailInput input){
        return service.updateNumber(customerId, email ,input);
    }

    @PutMapping("/up/{customerId}")
    public ResponseEntity<CartDetailDto> updateNumberUp(
            @PathVariable Integer customerId,
            String email,
            @Valid @RequestBody CartDetailInput input){
        return service.updateNumberUp(customerId,email ,input);
    }

    @PutMapping("/down/{customerId}")
    public ResponseEntity<CartDetailDto> updateNumberDown(@PathVariable Integer customerId,String email ,@Valid @RequestBody CartDetailInput input){
        return service.updateNumberDown(customerId, email ,input);
    }

    @PutMapping("/delete/{customerId}")
    public ResponseEntity<CartDetailDto> delete(@PathVariable Integer customerId,String email ,@Valid @RequestBody CartDetalInputDelete input){
        return service.delete(customerId, email ,input);
    }

    @GetMapping("/get-weight")
    public Float getWeight(Integer cartId, String email){
        return service.getAllWeight(cartId, email);
    }

    @GetMapping("/totalItem")
    public Integer getTotalItem(Integer id, String email){
        return service.getTotalItems(id, email);
    }

    @GetMapping("/soluongtronggio")
    public int getSoluongtronggio(Integer idcustomer, String email){
        return service.soluongtronggio(idcustomer, email);
    };
}
