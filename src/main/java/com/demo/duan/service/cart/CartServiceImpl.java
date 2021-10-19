package com.demo.duan.service.cart;

import com.demo.duan.entity.CartEntity;
import com.demo.duan.repository.cart.CartRepository;
import com.demo.duan.service.cart.CartService;
import com.demo.duan.service.cart.dto.CartDto;
import com.demo.duan.service.cart.mapper.CartMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository repository;

    private final CartMapper mapper;

    @Override
    @Transactional
    public ResponseEntity<CartDto> create() {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<CartDto> searchByCustomerId(Integer customerId) {
        CartEntity entity = repository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa đăng kí tài khoản"));
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }
}
