package com.demo.duan.controller;

import com.demo.duan.entity.LocalStorageBillDetail;
import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.cartdetail.CartDetailService;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
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

    private final CartDetailService cartDetailService;

    private final BillService billService;

    @PostMapping("/checkcart")
    public ResponseEntity<List<LocalStorageBillDetail>> checkCart(@Valid @RequestBody List<LocalStorageBillDetail> inputs){
        return service.checkCart(inputs);
    }

    @GetMapping("/check")
    public void check(String email){
        cartDetailService.check(email);
    };

    @GetMapping("/order/{id}")
    public ResponseEntity<BillDto> getOne(@PathVariable Integer id){
        return billService.getOne(id);
    }
}
