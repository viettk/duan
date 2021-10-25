package com.demo.duan.controller.discount;

import com.demo.duan.service.discount.DiscountService;
import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/discount")
@AllArgsConstructor
public class DiscountRest {
    private final DiscountService service;

    @PostMapping
    public ResponseEntity<DiscountDto> create(@Valid @RequestBody DiscountInput input){
        return service.create(input);
    }

    @PutMapping
    public ResponseEntity<DiscountDto> update(@PathVariable Integer id, @Valid @RequestBody DiscountInput input){
        return service.update(id, input);
    }

}
