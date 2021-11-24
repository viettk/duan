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
import java.util.Optional;

@RestController
@RequestMapping("/api/discount")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DiscountRest {
    private final DiscountService service;

    private final DiscountRepository repository;

    @GetMapping
    public ResponseEntity<Page<DiscountDto>> find(
            DiscountParam param,
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) Optional<String> known,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page){
        return service.find(param, field, known, limit, page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountDto> get(@PathVariable Integer id){
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<DiscountDto> create(@Valid @RequestBody DiscountInput input){
        return service.create(input);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountDto> update(@PathVariable Integer id, @Valid @RequestBody DiscountInput input){
        return service.update(id, input);
    }

    @GetMapping("/apdung")
    public Integer apdung(String discountName){
        return service.apdung(discountName);
    }
}
