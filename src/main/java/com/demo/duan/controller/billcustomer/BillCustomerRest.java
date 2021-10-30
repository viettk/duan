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
    public ResponseEntity<BillDto> createByCustomer(
            Integer cartId ,
            @Valid @RequestBody BillInput input,
            @RequestParam(name ="discountName", required = false) String discountName){
        System.out.println(cartId);
        System.out.println(discountName);
        return service.createByCustomer(cartId,input, discountName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> updateByCustomer(@PathVariable Integer id, @Valid @RequestBody BillInput input){
        return service.updateByCustomer(id, input);
    }
}
