package com.demo.duan.controller.billcustomer;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
@AllArgsConstructor
public class BillCustomerRest {

    private final BillService service;
    @GetMapping
    public ResponseEntity<List<BillDto>> getStatus(){
        return  service.getStatus();
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillDto> updateByCustomer(@PathVariable Integer id, @Valid @RequestBody BillInput input){
        System.out.println("ok");
        return service.updateByCustomer(id, input);
    }
}
