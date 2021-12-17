package com.demo.duan.controller.login;

import com.demo.duan.config.hashpassword.HashPass;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.param.CustomerMapper;
import com.demo.duan.service.jwt.*;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginRest {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private StaffMapper staffMapper;
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

    @PostMapping("/login")
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
                StaffDto staffDto = staffMapper.entityToDto(staff.get());
                if(staff.get().getRole()==1){
                    staffDto.setRole("ADMIN");
                }else {
                    staffDto.setRole("STAFF");
                }
                return ResponseEntity.ok(staffDto);
            }else {
                CustomerEntity customer = customerRepository.findByEmail(loginRequest.getEmail()).get();
                customer.setToken(jwt);
                CustomerDto customerDto = customerMapper.entityToDto(customer);
                customerDto.setRole("USER");
                return ResponseEntity.ok(customerDto);
            }
        }catch (Exception e){
            throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody Optional<String> token){
        try{
            String email = tokenProvider.getUserIdFromJWT(token.get().replace("\"",""),JWT_SECRET);
            CustomerEntity customer = customerRepository.findByEmail(email).orElse(null);
            if (customer != null) {
                customer.setLast_login(new Date());
                customerRepository.save(customer);
            }
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
