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
public class FavoriteRest {
    private final FavoriteService service;

    @GetMapping("/{customerId}")
    public ResponseEntity<Page<FavoriteDto>> find(@PathVariable Integer customerId ,FavoriteParam param, Optional<String> field, String known){
        return service.find(customerId ,param, field, known);
    }

    @PostMapping
    public ResponseEntity<FavoriteDto> create(@Valid @RequestBody FavoriteInput input){
        return service.create(input);
    }

    @DeleteMapping
    public ResponseEntity<FavoriteDto> update(
            @RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "productId", required = false) Integer productId
    ){
        return service.delete(customerId, productId);
    }
}
