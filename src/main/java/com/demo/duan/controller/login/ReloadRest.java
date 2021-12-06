package com.demo.duan.controller.login;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.param.CustomerMapper;
import com.demo.duan.service.jwt.JwtTokenProvider;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("api/reload")
@CrossOrigin(origins = "*")
public class ReloadRest {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Value("${secrert.login}")
    private String JWT_SECRET;
    @GetMapping
    public ResponseEntity<Object> reloadUser(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        bearerToken =bearerToken.substring(7);
        if (StringUtils.hasText(bearerToken) && tokenProvider.validateToken(bearerToken,JWT_SECRET)) {
            // Lấy id user từ chuỗi jwt

            String email = tokenProvider.getUserIdFromJWT(bearerToken, JWT_SECRET);
            Optional<StaffEntity> staff = staffRepository.findByEmail(email);
            if(staff.isPresent()){
                staff.get().setToken(bearerToken);
                StaffDto staffDto = staffMapper.entityToDto(staff.get());
                if(staff.get().getRole()==1){
                    staffDto.setRole("ADMIN");
                }else {
                    staffDto.setRole("STAFF");
                }
                return ResponseEntity.ok(staffDto);
            }else {
                CustomerEntity customer = customerRepository.findByEmail(email).get();
                customer.setToken(bearerToken);
                CustomerDto customerDto = customerMapper.entityToDto(customer);
                customerDto.setRole("USER");
                return ResponseEntity.ok(customerDto);
            }
        }else{
            return new ResponseEntity<Object>("Vui Lòng đăng nhập", HttpStatus.BAD_REQUEST);
        }
    }
}
