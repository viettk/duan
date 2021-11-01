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

    @GetMapping
    public ResponseEntity<List<AdressDto>> find(){
        return service.find();
    }

    @PostMapping
    public ResponseEntity<AdressDto> create(@Valid @RequestBody AdressInput input){
        return service.create(input);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdressDto> create(@PathVariable Integer id, @Valid @RequestBody AdressInput input){
        return service.update(id, input);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<AdressDto> delete(@PathVariable Integer id){
        return service.delete(id);
    }
}
