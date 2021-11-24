package com.demo.duan.controller.home;

import com.demo.duan.service.category.CategoryService;
import com.demo.duan.service.category.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/danh-muc/cha")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class HomeRest {
    private final CategoryService service;

    @GetMapping("/cha")
    public ResponseEntity<List<String>> findParent(){
        return service.findParent();
    }

    @GetMapping("/timtheocha")
    public ResponseEntity<List<CategoryDto>> getCatebyParen(String parentName){
        return service.getCatebyParen(parentName);
    }
}
