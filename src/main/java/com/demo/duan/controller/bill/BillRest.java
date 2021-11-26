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

    @GetMapping
    public ResponseEntity<Page<BillDto>>getAll(
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getAll(limit,page,field,known);
    }
    @GetMapping("/by-status")
    public ResponseEntity<Page<BillDto>>getByStatus(
            @RequestParam("pay") String pay,
            @RequestParam("order") String order,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getByStatus(pay, order, limit, page, field, known);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BillDto>getOne(@PathVariable("id") Integer id){
        return this.service.getOne(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Page<BillDto>>getByEmail(
            @PathVariable("email") String email,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getByEmail(email,limit,page,field,known);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto>updateBill(@PathVariable("id") Integer id, BillInput input){
        return this.service.update(input, id);
    }
    @PutMapping("/status-order/{id}")
    public ResponseEntity<BillDto>updateStatusOrder(
    			@PathVariable("id") Integer id,
    			@RequestBody BillInput input
    		){
    	return this.service.updateStatusOder(id, input);
    }
    @PostMapping("/status-pay/{id}")
    public ResponseEntity<BillDto>updateStatusPay(
    			@PathVariable("id") Integer id,
                @RequestBody BillInput input
    		){
    	return this.service.updateStatusPay(id, input);
    }
}
