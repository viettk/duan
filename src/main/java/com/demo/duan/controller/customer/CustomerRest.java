package com.demo.duan.controller.customer;

import com.demo.duan.service.customer.CustomerService;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/account")
@AllArgsConstructor
@RestController
public class CustomerRest {

    private final CustomerService service;

    @GetMapping("/get-email")
    public ResponseEntity<CustomerDto> getEmail(@RequestParam(name = "email") String email){
        return service.getEmail(email);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerInput input){
        return service.create(input);
    }

    @PutMapping("/update-password")
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody CustomerInput input){
        return service.update(input);
    }
}
