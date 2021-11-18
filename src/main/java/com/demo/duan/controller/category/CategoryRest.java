package com.demo.duan.controller.category;

import com.demo.duan.service.category.CategoryService;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/danh-muc")
@CrossOrigin(origins = "*")
public class CategoryRest {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<CategoryDto> find(){
        return null;
    };

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryInput input){
        return service.create(input);
    };

    @PutMapping
    public ResponseEntity<CategoryDto> update(@PathVariable Integer id, @Valid @RequestBody CategoryInput input){
        return service.update(id, input);
    }

    @GetMapping("/cha")
    public ResponseEntity<List<String>> findParent(){
        return service.findParent();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAll() {
        return service.getAll();
    }
}
