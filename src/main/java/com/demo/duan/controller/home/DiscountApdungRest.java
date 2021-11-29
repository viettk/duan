package com.demo.duan.controller.home;

import com.demo.duan.service.discount.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DiscountApdungRest {
    private final DiscountService service;

    @GetMapping("/apdung")
    public Integer apdung(String discountName){
        return service.apdung(discountName);
    }
}
