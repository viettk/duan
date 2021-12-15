package com.demo.duan.controller.home;

import com.demo.duan.service.category.CategoryService;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.param.CategoryParam;
import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/danh-muc/cha")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class HomeRest {
    private final CategoryService service;

    private final ProductService productService;

    @GetMapping("/cha")
    public ResponseEntity<List<String>> findParent(){
        return service.findParent();
    }

    @GetMapping("/timtheocha")
    public ResponseEntity<List<CategoryDto>> getCatebyParen(String parentName){
        return service.getCatebyParen(parentName);
    }

    @GetMapping("/namedm")
    public ResponseEntity<List<CategoryDto>> finCate(String name){
        return service.getAllCate(name);
    }
}
