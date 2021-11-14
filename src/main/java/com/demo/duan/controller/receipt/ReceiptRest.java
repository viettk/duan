package com.demo.duan.controller.receipt;

import com.demo.duan.service.receipt.ReceiptService;
import com.demo.duan.service.receipt.dto.ReceiptDto;
import com.demo.duan.service.receipt.input.ReceiptInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping("/rest/receipt")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReceiptRest {
    private final ReceiptService service;
    //    hiển thị phiếu nhập
    @GetMapping("/view")
    public ResponseEntity<Page<ReceiptDto>> getALl(Pageable pageable){
        System.out.println("done");
        return this.service.getAll(pageable);
    }
    //    hiển thị phiếu nhập theo id
    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDto> getOne(@PathVariable Integer id ){
        System.out.println("ok");
        return this.service.getOne(id);
    };
    @GetMapping("/search")
    public ResponseEntity<ReceiptDto> findByDate(@RequestParam ("date_day") Date date){
        System.out.println("da nhan ngay");
        return this.service.findByDate(date);
    }
    //    @GetMapping("/search_day")
//    public ResponseEntity<Page<ReceiptDto>> findDate(@RequestParam ("start_day") Date getStartOfDay , @RequestParam("end_day") Date getEndOfDay){
//        System.out.println("da nhan ngay");
//        return this.service.findDate(getStartOfDay,getEndOfDay);
//    }
//    thêm phiếu nhập
    @PostMapping("/add")
    public ResponseEntity<ReceiptDto> create(@Valid @RequestBody ReceiptInput input) {
        System.out.println("ok");
        return this.service.create(input);
    }
    //    cập nhật phiếu nhật
    @PutMapping("/{id}")
    public  ResponseEntity<ReceiptDto> update(@PathVariable Integer id ,@Valid @RequestBody ReceiptInput input){
        return service.update(id , input);
    }

}
