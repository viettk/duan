package com.demo.duan.controller.customer;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.service.customer.CustomerService;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import com.demo.duan.service.customer.param.CustomerParam2;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/account")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class CustomerRest {

    private final CustomerService service;
    private final CustomerRepository repository;

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

    @GetMapping("/admin/list-id")
    public ResponseEntity<List<Integer>> findAllListID(
            @RequestParam(name = "email") Optional<String> email,
            @RequestParam(name = "status") Optional<Boolean> status,
            @RequestParam(name = "name") Optional<String> name
    ){
        List<CustomerEntity> entities = repository.findAllCustomer_ID(email.orElse(""),status.orElse(null),name.orElse(""));
        List<Integer> listID = new ArrayList<>();
        for(CustomerEntity s : entities){
            listID.add((Integer) s.getId());
        }
        return ResponseEntity.ok(listID);
    }

    @GetMapping("/admin/bybill")
    public ResponseEntity<Page<Map<String,Object>>> findAll_ByBill(CustomerParam2 param2){
        return service.fillAllBy_Bill(param2);
    }

    @GetMapping("/admin/list-id-bybill")
    public ResponseEntity<List<Integer>> fillAllIDTK(CustomerParam2 customerParam2){
        List<Integer> listID = new ArrayList<>();
        List<Map<String,Object>> map = repository.findCustomer_Bill_ID(customerParam2);
        for(Map<String,Object> s : map){
            listID.add((Integer) s.get("id"));
        }
        return ResponseEntity.ok(listID);
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
