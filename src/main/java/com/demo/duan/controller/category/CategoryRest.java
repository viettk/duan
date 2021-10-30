package com.demo.duan.controller.category;

import com.demo.duan.service.category.CategoryService;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import com.demo.duan.service.category.param.CategoryParam;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/danh-muc")
public class CategoryRest {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> find(CategoryParam param){
        return service.find(param);
    };

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Integer id){
        return service.get(id);
    };


    @GetMapping("/cha")
    public ResponseEntity<List<String>> findParent(){
        return service.findParent();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryInput input){
        return service.create(input);
    };

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Integer id, @Valid @RequestBody CategoryInput input){
        return service.update(id, input);
    }
}
