package com.demo.duan.controller.home;

import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/all-product-customer")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class HomeAllProduct {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAll(
            String productName,
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) Optional<String> known,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page
    ){
        return service.getAllproduct(productName, field, known, limit, page);
    }


    @GetMapping("/notsearch")
    public ResponseEntity<Page<ProductDto>> getAll(
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) Optional<String> known,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page
    ){
        return service.getAllNotSearch( field, known, limit, page);
    }
}
