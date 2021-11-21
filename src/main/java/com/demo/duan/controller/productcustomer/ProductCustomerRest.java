package com.demo.duan.controller.productcustomer;

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
@RequestMapping("/home")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProductCustomerRest {
    private final ProductService service;

    @GetMapping("/new-arrival")
    public ResponseEntity<List<ProductDto>> searchNewArrival(){
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
    public ResponseEntity<Page<ProductDto>> searchByKhac(
            ProductParam param,
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) Optional<String> known,
            @RequestParam(name = "_limit", required = false) Optional<Integer> limit,
            @RequestParam(name = "_page", required = false) Optional<Integer> page
    ){
        return service.searchByKhac(param, field, known, limit, page);
    }

    @GetMapping("/cha/{parent}")
    public ResponseEntity<Page<ProductDto>> getByParentName(
            CategoryParam cate,
            ProductParam param,
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) String known,
            @RequestParam(name = "_page", required = false) Optional<Integer> page,
            @RequestParam(name = "_limit", required = false) Optional<Integer> limit
    ){
        return service.searchAllByParent(cate, param, field, known, limit, page);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDto>> searchByCategoryName(
            @PathVariable Integer categoryId,
            ProductParam param,
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) String known,
            @RequestParam(name = "_page", required = false) Optional<Integer> page,
            @RequestParam(name = "_limit", required = false) Optional<Integer> limit){
        return service.searchByCategoryName(categoryId, param, field, known, limit, page);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @GetMapping("/top5sp")
    public ResponseEntity<List<ProductDto>> getTop5sp(){
        return service.Thongketop5spbanchay();
    }

    @GetMapping("/return/{id}")
    public ResponseEntity<ProductDto>returnProduct(
            @PathVariable("id") Integer id,
            @RequestParam("number") Integer number
    ){
        return this.service.returnNumber(id, number);
    }
}
