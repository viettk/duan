package com.demo.duan.controller.receiptdetail;

import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.receiptDetail.ReceiptDetailService;
import com.demo.duan.service.receiptDetail.dto.ReceiptDetailDto;
import com.demo.duan.service.receiptDetail.input.ReceiptDetailInput;
import com.demo.duan.service.receiptDetail.param.ReceiptDetailparam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receiptDetail")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReceiptDetailRest {
    private final ReceiptDetailService service;
    private final ProductService productService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ReceiptDetailDto> getID(@PathVariable Integer id){
        return service.getId(id);
    }

    @GetMapping("/{receiptId}")
    public ResponseEntity<Page<ReceiptDetailDto>> getAll(
            @PathVariable Integer receiptId,
            ReceiptDetailparam param,
            @RequestParam(name = "_page", required = false) Optional<Integer> page,
            @RequestParam(name = "_limit", required = false) Optional<Integer> limit                                                         ){
        return this.service.searchByAdmin(receiptId, param, limit, page);
    }
    @PostMapping("/add")
    public ResponseEntity<ReceiptDetailDto> create(@Valid @RequestBody ReceiptDetailInput input){
        return service.create(input);
    }
    /*cập nhật phiếu nhập chi tiết*/
    @PutMapping("/{id}")
        public  ResponseEntity<ReceiptDetailDto> update(@PathVariable Integer id , @Valid @RequestBody ReceiptDetailInput input ){
        return service.update(id , input);
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<ReceiptDetailDto> delete(
            @PathVariable Integer id,
            Integer receiptId){
        return service.deleteById(id, receiptId);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> find(String name){
        return productService.findAll(name);
    }
}
