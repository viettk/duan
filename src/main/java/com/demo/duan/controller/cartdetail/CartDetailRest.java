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
    public ResponseEntity<List<CartDetailDto>> find(@PathVariable Integer customerId){
        return service.find(customerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDetailDto> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<CartDetailDto> addToCart(@PathVariable Integer customerId, @Valid @RequestBody CartDetailInput input){
        return service.addToCartDetail(customerId, input);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CartDetailDto> updateNumber(@PathVariable Integer customerId, @Valid @RequestBody CartDetailInput input){
        return service.updateNumber(customerId, input);
    }

    @PutMapping("/up/{customerId}")
    public ResponseEntity<CartDetailDto> updateNumberUp(@PathVariable Integer customerId, @Valid @RequestBody CartDetailInput input){
        return service.updateNumberUp(customerId, input);
    }

    @PutMapping("/down/{customerId}")
    public ResponseEntity<CartDetailDto> updateNumberDown(@PathVariable Integer customerId, @Valid @RequestBody CartDetailInput input){
        return service.updateNumberDown(customerId, input);
    }

    @DeleteMapping("delete/{customerId}")
    public ResponseEntity<CartDetailDto> delete(@PathVariable Integer customerId, @Valid @RequestBody CartDetalInputDelete input){
        return service.delete(customerId,input);
    }

    @GetMapping("/get-weight")
    public Float getWeight(Integer cartId){
        return service.getAllWeight(cartId);
    }

    @GetMapping("/totalItem")
    public Integer getTotalItem(Integer id){
        return service.getTotalItems(id);
    }

    @GetMapping("/soluongtronggio")
    public Integer getSoluongtronggio(Integer idcustomer){
        return service.soluongtronggio(idcustomer);
    };
}
