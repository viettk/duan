package com.demo.duan.controller.productadmin;

import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/san-pham")
@CrossOrigin(origins = "*")
public class ProductAdminRest {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> searchByAdmin(
            ProductParam param,
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) Optional<String> known,
            @RequestParam(name = "_limit", required = false) Optional<Integer> limit,
            @RequestParam(name = "_page", required = false) Optional<Integer> page
    ){
        return service.searchByAdmin(param, field, known, limit, page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Integer id){
        return service.getOneAdmin(id);
    }
}
