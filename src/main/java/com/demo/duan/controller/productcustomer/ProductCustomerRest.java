package com.demo.duan.controller.productcustomer;

import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ProductCustomerRest {
    private final ProductService service;

    @GetMapping("/new-arrival")
    public ResponseEntity<Page<ProductDto>> searchNewArrival(){
        return service.searchNewArrival();
    }

    @GetMapping("/model-kit")
    public ResponseEntity<Page<ProductDto>> searchbyModelKit(){
        return service.searchByModelKit();
    }

    @GetMapping("/static-model")
    public ResponseEntity<Page<ProductDto>> searchByStaticModel(){
        return service.searchByStaticModel();
    }

    @GetMapping("/shf")
    public ResponseEntity<Page<ProductDto>> searchBySHF(){
        return service.searchBySHF();
    }

    @GetMapping("/category")
    public ResponseEntity<Page<ProductDto>> searchByCategoryName(ProductParam param, Optional<String> field, String known){
        return service.searchByCategoryName(param, field, known);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }
}
