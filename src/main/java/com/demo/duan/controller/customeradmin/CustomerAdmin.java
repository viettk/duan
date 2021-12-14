package com.demo.duan.controller.customeradmin;

import com.demo.duan.service.customer.CustomerService;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.paramcustomer.Customerparam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/admin/customer")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerAdmin {
    private final CustomerService service;

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> getAll(
            Customerparam param,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page){
        return service.getAll(param, limit,page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateStatus(@PathVariable("id") Integer id){
        return service.updateStatus(id);
    }
}
