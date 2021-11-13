package com.demo.duan.controller.login;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/login")
@CrossOrigin("*")
public class LoginRest {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken(loginRequest.getEmail());
        Optional<StaffEntity> staff = staffRepository.findByEmail(loginRequest.getEmail());
        if(staff.isPresent()){
            staff.get().setToken(jwt);
            return ResponseEntity.ok(staff);
        }else {
            CustomerEntity customer = customerRepository.findByEmail(loginRequest.getEmail()).get();
            customer.setToken(jwt);
            return ResponseEntity.ok(customer);
        }
    }

}
