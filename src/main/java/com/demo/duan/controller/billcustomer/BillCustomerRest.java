package com.demo.duan.controller.billcustomer;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
@AllArgsConstructor
public class BillCustomerRest {

    private final BillService service;

    @PostMapping
    public ResponseEntity<BillDto> createByCustomer(@Valid @RequestBody BillInput input){
        System.out.println("done");
        return service.createByCustomer(input);
    }
}
