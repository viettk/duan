package com.demo.duan.controller.favorite;

import com.demo.duan.service.favorite.FavoriteService;
import com.demo.duan.service.favorite.dto.FavoriteDto;
import com.demo.duan.service.favorite.input.FavoriteInput;
import com.demo.duan.service.favorite.param.FavoriteParam;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/yeu-thich")
@CrossOrigin(origins = "*")
public class FavoriteRest {
    private final FavoriteService service;

    /* Sản phẩm có bao nhiêu lượt thích */
    @GetMapping("/sp/{id}")
    public int getProduct(@PathVariable Integer id){
        return service.getProduct(id);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Page<FavoriteDto>> find(@PathVariable Integer customerId ,FavoriteParam param, Optional<String> field, String known){
        return service.find(customerId ,param, field, known);
    }

    @GetMapping("/san-pham-yeu-thich")
    public int getOne( Integer id, Integer product_id, String email){
        return service.getOne(id , product_id, email);
    }

    @PostMapping
    public ResponseEntity<FavoriteDto> create(@Valid @RequestBody FavoriteInput input){
        return service.create(input);
    }

    @PostMapping("/y")
    public ResponseEntity<FavoriteDto> update(@Valid @RequestBody FavoriteInput input) {
        System.out.println(input.getProductId() +"cnfdifi");
        return service.delete(input);}
}
