package com.demo.duan.controller.productcustomer;

import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detail")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductDetailRest {

    private final ProductService service;

    @GetMapping("/goi-y")
    public ResponseEntity<List<ProductDto>> goiySP(Integer priceProduct){
        return service.relatedProducts(priceProduct);
    }
}
