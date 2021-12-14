package com.demo.duan.controller.address;

import com.demo.duan.service.address.AdressService;
import com.demo.duan.service.address.dto.AdressDto;
import com.demo.duan.service.address.input.AdressInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/dia-chi")
@CrossOrigin(origins = "*")
public class AdressRest {

    private final AdressService service;

    @GetMapping("/all/{customer_id}")
    public ResponseEntity<List<AdressDto>> find(@PathVariable Integer customer_id, String email){
        return service.find(customer_id, email);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<AdressDto> getOne(@PathVariable Integer customer_id, String email){
        return service.getOne(customer_id, email);
    }

    @PostMapping
    public ResponseEntity<AdressDto> create(@Valid @RequestBody AdressInput input, String email){
        return service.create(input, email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdressDto> update(
            @PathVariable Integer id,
            @RequestParam("email") String email,@Valid @RequestBody AdressInput input){
        System.out.println(email);
        return service.update(id, email ,input);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<AdressDto> delete(@PathVariable Integer id, String email){
        return service.delete(id, email);
    }

    @GetMapping("/mac-dinh")
    public ResponseEntity<AdressDto> get(Integer customerId, String email){
        return service.getMacdinh(customerId, email);
    }
}
