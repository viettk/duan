package com.demo.duan.controller.category;

import com.demo.duan.service.category.CategoryService;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import com.demo.duan.service.category.param.CategoryParam;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/danh-muc")
public class CategoryRest {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<CategoryDto> find(CategoryParam param, Pageable pageable){
        return service.find(param, pageable);
    };

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryInput input){
        return service.create(input);
    };

    @PutMapping
    public ResponseEntity<CategoryDto> update(@PathVariable Integer id, @Valid @RequestBody CategoryInput input){
        return service.update(id, input);
    }
}
