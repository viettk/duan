package com.demo.duan.controller.receiptdetail;


import com.demo.duan.service.receiptDetail.ReceiptDetailService;
import com.demo.duan.service.receiptDetail.dto.ReceiptDetailDto;
import com.demo.duan.service.receiptDetail.input.ReceiptDetailInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/receiptDetail")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReceiptDetailRest {
    private  final ReceiptDetailService service;
    @GetMapping("/me")
    public ResponseEntity<Page<ReceiptDetailDto>> getAll(Pageable pageable){
        System.out.println("ok");
        return this.service.getAll(pageable);
    };
    //    @GetMapping("/{id}")
//    public ResponseEntity<ReceiptDetailDto> findByIdRecript(@PathVariable Integer id){
//        System.out.println("da nhan id");
//        return this.service.findByIdRecript(id);
//    }
    /* thêm phiếu nhập chi tiết*/
    @PostMapping("/add")
    public ResponseEntity<ReceiptDetailDto> create(@Valid @RequestBody ReceiptDetailInput input){
        System.out.println("done");
        return service.create(input);
    }
    /*cập nhật phiếu nhập chi tiết*/
    @PutMapping("/{id}")
    public  ResponseEntity<ReceiptDetailDto> update(@PathVariable Integer id , @Valid @RequestBody ReceiptDetailInput input ){
        System.out.println("ok chưa");
        return service.update(id , input);
    }
}
