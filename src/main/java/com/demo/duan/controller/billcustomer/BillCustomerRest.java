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

    @PostMapping("/dat/{cartId}")
    public ResponseEntity<BillDto> createByCustomer(
            @PathVariable Integer cartId ,
            @Valid @RequestBody BillInput input){
        return service.createByCustomer(cartId,input);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> updateByCustomer(@PathVariable Integer id, @Valid @RequestBody BillInput input){
        return service.updateByCustomer(id, input);
    }
}
