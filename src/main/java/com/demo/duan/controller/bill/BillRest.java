package com.demo.duan.controller.bill;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/bill")
public class BillRest {

    private final BillService service;

    @GetMapping("/get")
    public ResponseEntity<Page<BillDto>>getAll(
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getAll(limit,page,field,known);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<BillDto>getOne(@PathVariable("id") Integer id){
        return this.service.getOne(id);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<Page<BillDto>>getByEmail(
            @PathVariable("email") String email,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getByEmail(email,limit,page,field,known);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BillDto>updateBill(@PathVariable("id") Integer id, BillInput input){
        return this.service.update(input, id);
    }
}
