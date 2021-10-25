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
@CrossOrigin(origins = "*")
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

    @GetMapping("/khac")
    public ResponseEntity<Page<ProductDto>> searchByKhac(){
        return service.searchBySHF();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDto>> searchByCategoryName(
            @PathVariable Integer categoryId,
            ProductParam param,
            @RequestParam(name = "field", required = false) Optional<String> field,
            @RequestParam(name = "known", required = false) String known){
        return service.searchByCategoryName(categoryId, param, field, known);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }
}
