package com.demo.duan.controller.product;

import com.demo.duan.entity.ProductEntity;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.input.ProductCreateInput;
import com.demo.duan.service.product.input.ProductUpdateInput;
import com.demo.duan.service.product.mapper.ProductMapper;
import com.demo.duan.service.product.mapper.ProductUpdateMapper;
import com.demo.duan.service.product.param.ProductParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProductRest {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    @GetMapping
    public ResponseEntity<Page<ProductDto>> fillProduct(
            ProductParam param,
            @RequestParam(name = "_field", required = false) Optional<String> field,
            @RequestParam(name = "_known", required = false) Optional<String> known,
            @RequestParam(name = "_limit", required = false) Optional<Integer> limit,
            @RequestParam(name = "_page", required = false) Optional<Integer> page
    ){
        return productService.searchProduct(param, field, known, limit, page);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ProductEntity>> getProductAll(){
        return ResponseEntity.ok(productRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Integer id){
        ProductEntity product=  productRepository.getById(id);
        return ResponseEntity.ok(mapper.entityToDto(product));
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> createProduct(@Valid @RequestBody ProductCreateInput input)  {
        return productService.createProduct(input);
    }

    @PostMapping("/insert/image/{id}")
    public ResponseEntity<ProductDto> insertImage(@PathVariable("id") Integer id,@PathParam("photo1") Optional<MultipartFile> photo1, @PathParam("photo2") Optional<MultipartFile> photo2, @PathParam("photo3") Optional<MultipartFile> photo3, @PathParam("photo4") Optional<MultipartFile> photo4, @PathParam("product") String product) throws JsonProcessingException {
        return productService.insertImage(id,"du-an-front-end/public/images",photo1,photo2,photo3,photo4);
    }

    @PostMapping("/create/test")
    public ResponseEntity<Object> createProductTest(@Valid @RequestBody ProductCreateInput input,@RequestParam("photo1") Optional<MultipartFile> photo1 ){
        System.out.println(1);
        System.out.println(input.getName());
        return ResponseEntity.ok(null);
    }
    @DeleteMapping("/delete/{ids}")
    public ResponseEntity<List<ProductDto>> hideProduct(@PathVariable("ids") Integer[] ids) {
       return productService.hideProduct(ids);
    }
    @PutMapping("/present/{ids}")
    public ResponseEntity<List<ProductDto>> presentProduct(@PathVariable("ids") Integer[] ids) {
        return productService.presentProduct(ids);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> upddateProduct(@PathVariable("id") Integer id,@PathParam("photo1") Optional<MultipartFile> photo1, @PathParam("photo2") Optional<MultipartFile> photo2, @PathParam("photo3") Optional<MultipartFile> photo3, @PathParam("photo4") Optional<MultipartFile> photo4, @PathParam("product") String product) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ProductUpdateInput productUpdateInput = mapper.readValue(product, ProductUpdateInput.class);
        return productService.updateProduct("/src/main/resources/images",id,productUpdateInput,photo1,photo2,photo3,photo4);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProductDto> hiddenOneProduct(@PathVariable Integer id){
        return productService.hiddenOneProduct(id);
    }
}
