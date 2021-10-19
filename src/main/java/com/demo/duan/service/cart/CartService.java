package com.demo.duan.service.cart;

import com.demo.duan.service.cart.dto.CartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    public ResponseEntity<CartDto> create();

    public ResponseEntity<CartDto> searchByCustomerId(Integer customerId);
}
