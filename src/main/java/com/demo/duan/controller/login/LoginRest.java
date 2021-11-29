package com.demo.duan.controller.login;

import com.demo.duan.config.hashpassword.HashPass;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.customer.param.CustomerMapper;
import com.demo.duan.service.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/login")
@CrossOrigin(origins = "*")
public class LoginRest {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 600000000L;
    @Value("${secrert.login}")
    private String JWT_SECRET;

    @Autowired
    private CustomerMapper mapper;

    @PostMapping
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Map<String, String> errors = new HashMap<>();
        if(customerRepository.findByEmailAndStatusIsFalse(loginRequest.getEmail()).isPresent()
                || staffRepository.findByEmailAndStatusIsFalse(loginRequest.getEmail()).isPresent()){
            errors.put("email", "Tài khoản đã bị vô hiệu hóa");
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            HashPass.hash(loginRequest.getPassword())
                    )
            );
            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken(loginRequest.getEmail(),JWT_EXPIRATION,JWT_SECRET);
            Optional<StaffEntity> staff = staffRepository.findByEmail(loginRequest.getEmail());
            if(staff.isPresent()){
                staff.get().setToken(jwt);
                return ResponseEntity.ok(staff);
            }else {
                CustomerEntity customer = customerRepository.findByEmail(loginRequest.getEmail()).get();
                customer.setToken(jwt);
                return ResponseEntity.ok(mapper.entityToDto(customer));
            }
        }catch (Exception e){
            throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
        }
    }

}
