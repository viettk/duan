package com.demo.duan.controller.login;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.param.CustomerMapper;
import com.demo.duan.service.jwt.JwtTokenProvider;
import com.demo.duan.service.login_google.GooglePojo;
import com.demo.duan.service.login_google.GoogleUtils;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/login-google")
@CrossOrigin(origins = "*")
public class LoginGoogleRest {
    @Autowired
    private GoogleUtils googleUtils;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private StaffMapper staffMapper;
    private final long JWT_EXPIRATION = 604800000L;
    @Value("${secrert.login}")
    private String JWT_SECRET;

    @PostMapping
    public ResponseEntity<Object> googleLogin(@RequestBody String accessToken) throws IOException {
        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        Optional<StaffEntity> staff = staffRepository.findByEmail(googlePojo.getEmail());
        String jwt = tokenProvider.generateToken(googlePojo.getEmail(),JWT_EXPIRATION,JWT_SECRET);
        if(staff.isPresent()){
            staff.get().setToken(jwt);
            StaffDto staffDto = staffMapper.entityToDto(staff.get());
            if(staff.get().getRole()==1){
                staffDto.setRole("ADMIN");
            }else {
                staffDto.setRole("STAFF");
            }
            return ResponseEntity.ok(staffDto);
        }
        Optional<CustomerEntity> customer = customerRepository.findByEmail(googlePojo.getEmail());
        if(customer.isPresent()){
            customer.get().setToken(jwt);
            CustomerDto customerDto = customerMapper.entityToDto(customer.get());
            customerDto.setRole("USER");
            return ResponseEntity.ok(customerDto);
        }
        CustomerEntity customerEntity = new CustomerEntity(null,googlePojo.getEmail(),accessToken,googlePojo.getName(),null,true,null,null,null);
        CustomerEntity customerNew = customerRepository.save(customerEntity);
        customerNew.setToken(jwt);
        CustomerDto customerDto = customerMapper.entityToDto(customerNew);
        customerDto.setRole("USER");
        return ResponseEntity.ok(customerDto);
    }
}
