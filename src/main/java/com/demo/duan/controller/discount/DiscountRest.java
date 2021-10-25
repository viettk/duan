package com.demo.duan.controller.discount;

import com.demo.duan.entity.DiscountEntity;
import com.demo.duan.repository.discount.DiscountRepository;
import com.demo.duan.service.discount.DiscountService;
import com.demo.duan.service.discount.dto.DiscountDto;
import com.demo.duan.service.discount.input.DiscountInput;
import com.demo.duan.service.discount.param.DiscountParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/discount")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DiscountRest {
    private final DiscountService service;

    private final DiscountRepository repository;

    @GetMapping
    public ResponseEntity<Page<DiscountDto>> find(DiscountParam param, Pageable pageable){
        DiscountEntity entity = repository.getById(1);
        System.out.println(entity.getName());
        return service.find(param, pageable);
    }

    @PostMapping
    public ResponseEntity<DiscountDto> create(@Valid @RequestBody DiscountInput input){
        return service.create(input);
    }

    @PutMapping
    public ResponseEntity<DiscountDto> update(@PathVariable Integer id, @Valid @RequestBody DiscountInput input){
        return service.update(id, input);
    }

}
