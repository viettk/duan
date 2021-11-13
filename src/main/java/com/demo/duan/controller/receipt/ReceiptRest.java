package com.demo.duan.controller.receipt;

import com.demo.duan.service.receipt.ReceiptService;
import com.demo.duan.service.receipt.dto.ReceiptDto;
import com.demo.duan.service.receipt.input.ReceiptInput;
import com.demo.duan.service.receipt.param.ReceiptParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("/api/receipt")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReceiptRest {
    private final ReceiptService service;

    @GetMapping
    public ResponseEntity<Page<ReceiptDto>> getALl(
            Integer month,
            Integer year,
            @RequestParam("_field") Optional<String> field,
            @RequestParam("_known") Optional<String> known,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page){
        return this.service.getAll(month, year, field, known, limit, page);
    }

    @GetMapping("/{id}")

    public ResponseEntity<ReceiptDto> getOne(@PathVariable Integer id ){
        return this.service.getOne(id);
    };
    @GetMapping("/search")
    public ResponseEntity<ReceiptDto> findByDate(@RequestParam ("date_day") Date date){
        return this.service.findByDate(date);
    }

    @PostMapping
    public ResponseEntity<ReceiptDto> create(@Valid @RequestBody ReceiptInput input) {
        return this.service.create(input);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ReceiptDto> update(@PathVariable Integer id ,@Valid @RequestBody ReceiptInput input){
        return service.update(id , input);
    }

}
