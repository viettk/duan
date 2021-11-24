package com.demo.duan.controller;

import com.demo.duan.entity.LocalStorageBillDetail;
import com.demo.duan.service.checkcartlocal.CheckCartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/check")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class CheckCart {

    private final CheckCartService service;

    @PostMapping("/checkcart")
    public ResponseEntity<List<LocalStorageBillDetail>> checkCart(@Valid @RequestBody List<LocalStorageBillDetail> inputs){
        return service.checkCart(inputs);
    }
}
