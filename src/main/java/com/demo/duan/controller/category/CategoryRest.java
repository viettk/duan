package com.demo.duan.controller.category;

import com.demo.duan.service.category.CategoryService;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import com.demo.duan.service.category.param.CategoryParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/danh-muc")
public class CategoryRest {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> find(
            CategoryParam param,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page){
        return service.find(param, limit, page);
    };

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAll(){
        return service.getAll();
    }

    @GetMapping("/all/search")
    public ResponseEntity<List<CategoryDto>> getAllSearch(
            CategoryParam param
    ){
        return service.getAllSearch(param);
    }

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

    @GetMapping("/kit")
    public ResponseEntity<List<CategoryDto>> getKit(){
        return service.getKit();
    }

    @GetMapping("/timtheocha")
    public ResponseEntity<List<CategoryDto>> getCatebyParen(String parentName){
        return service.getCatebyParen(parentName);
    }

}
