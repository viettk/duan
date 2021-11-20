package com.demo.duan.controller.customer;

import com.demo.duan.service.customer.CustomerService;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/account")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerRest {

    private final CustomerService service;
    @GetMapping
    public  ResponseEntity<Page<CustomerDto>> getAll(Pageable pageable,@RequestParam("_limit") Optional<Integer> limit,
                                                     @RequestParam("_page") Optional<Integer> page){
        return service.getAll(pageable, limit,page);
    }
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
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateStatus(@PathVariable("id") Integer id){
        return service.updateStatus(id);
    }
}
