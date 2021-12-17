package com.demo.duan.controller.customer;

import com.demo.duan.service.customer.CustomerService;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/account")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class CustomerRest {

    private final CustomerService service;

    @GetMapping("/get-email")
    public ResponseEntity<CustomerDto> getEmail(@RequestParam(name = "email") String email){
        return service.getEmail(email);
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<CustomerDto>> findAll(
            @RequestParam(name = "email") Optional<String> email,
            @RequestParam(name = "status") Optional<Boolean> status,
            @RequestParam(name = "known") Optional<String> known,
            @RequestParam(name = "page") Optional<Integer> page,
             @RequestParam(name = "field") Optional<String> field,
            @RequestParam(name = "name") Optional<String> name
    ){
        return service.fillAll(email.orElse(""),status.orElse(null),known.orElse(null),field.orElse("id"),name.orElse(""),page.orElse(0));
    }

    @DeleteMapping("/admin/hide")
    public ResponseEntity<Void> hide(@RequestParam(name = "id") Integer id){
        return service.disable(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> create(@Valid @RequestBody CustomerInput input){
        return service.create(input);
    }

    @PutMapping("/update-password")
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody CustomerInput input){
        return service.update(input);
    }
}
