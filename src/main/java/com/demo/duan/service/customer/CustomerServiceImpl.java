package com.demo.duan.service.customer;

import com.demo.duan.config.hashpassword.HashPass;
import com.demo.duan.entity.CartEntity;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.repository.cart.CartRepository;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import com.demo.duan.service.customer.param.CustomerMapper;
import com.demo.duan.service.customer.param.CustomerParam2;
import com.demo.duan.service.customer.paramcustomer.Customerparam;
import com.demo.duan.service.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private  CustomerRepository repository;
    @Autowired
    private  CustomerMapper mapper;
    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    private  JwtTokenProvider jwtTokenProvider;
    private final long JWT_EXPIRATION = 604800000L;
    @Value("${secrert.login}")
    private String JWT_SECRET;

    @Override
    @Transactional
    public ResponseEntity<Object> create(CustomerInput input) {
        Map<String, String> errors = new HashMap<>();
        /* Kiểm tra email đã tồn tại hay chưa */
        if(repository.findByEmail(input.getEmail()).isPresent()){
            errors.put("email", "Email của bạn có vẻ không ổn, mời dùng tài khoản khác");
        }
        /* Kiểm tra nhập lại mật khẩu */
        if(!input.getPassword().equals(input.getRepeatPassword())){
//          throw new RuntimeException("Mật khẩu không khớp nhau")
            errors.put("repeatPassword", "Mật khẩu không khớp");
        }
        if(input.getPassword().length()<6){
            errors.put("password", "Mật khẩu tối thiểu 6 ký tự");
        }
        if(input.getPassword().length()>15){
            errors.put("password", "Mật khẩu tối đa 15 ký tự");
        }


        if(!errors.isEmpty()){
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }

        /* Gửi email, nếu ko đc -> email ko tồn tại */

        Date day_date = new Date();

        /* Input -> Entity và set các trường của Entity */
        String password = HashPass.hash(input.getPassword());
        CustomerEntity entity = mapper.inputToEntity(input);
        entity.setPassword(password);
        entity.setLast_login(day_date);

        /* Lưu khách hàng vào db */
        repository.save(entity);

        /* Tạo giỏ hàng cho khách vừa đăng kí */
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCustomer(entity);
        cartEntity.setCreate_date(day_date);
        cartEntity.setTotal(BigDecimal.ZERO);

        /* Lưu giỏ hàng vào DB */
        cartRepository.save(cartEntity);
        entity.setToken(jwtTokenProvider.generateToken(entity.getEmail(), JWT_EXPIRATION,JWT_SECRET));
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }


    @Override
    @Transactional
    public ResponseEntity<CustomerDto> update(CustomerInput input) {

        CustomerEntity entity = repository.findByEmail(input.getEmail())
                .orElseThrow(()-> new RuntimeException("Email không tồn tại"));

        /* Kiểm tra nhập lại mật khẩu */
        if(!input.getPassword().equals(input.getRepeatPassword())){
            throw new RuntimeException("Mật khẩu không khớp nhau");
        }
        mapper.inputToEntity(input, entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<CustomerDto> getEmail(String email) {
        CustomerEntity entity = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<Page<CustomerDto>> getAll(Customerparam param, Optional<Integer> limit, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), Sort.by(Sort.Direction.DESC, "id"));
        Page<CustomerDto> entity = repository.getAll(param, pageable).map(mapper :: entityToDto);
        return ResponseEntity.ok().body(entity);
    }

    @Override
    @Transactional
    public ResponseEntity<CustomerDto> updateStatus(Integer id) {
        CustomerEntity entity = repository.getById(id);
        Boolean status = entity.isStatus();
        if(status == true){
            entity.setStatus(false);
        }else {
            entity.setStatus(true);
        }
        repository.save(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    // ----------------------------------------------------

    @Override
    @Transactional
    public ResponseEntity<Void> disable(Integer id) {
        Optional<CustomerEntity> entity = repository.findById(id);
        if(entity.isPresent()){
            if(entity.get().isStatus()==true){
                entity.get().setStatus(false);
            }else{
                entity.get().setStatus(true);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Page<CustomerDto>> fillAll(String email,Boolean status,String known,String field,String name,Integer page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field);
            Pageable pageable = PageRequest.of(page, 10, sort);
            Page<CustomerDto> customerDtos = repository.findAllCustomer(email,status,name,pageable).map(mapper::entityToDto);
            return  ResponseEntity.ok(customerDtos);
        }else{
            Sort sort =Sort.by(Sort.Direction.DESC, field);
            Pageable pageable = PageRequest.of(page, 10, sort);
            Page<CustomerDto> customerDtos = repository.findAllCustomer(email,status,name,pageable).map(mapper::entityToDto);
            return  ResponseEntity.ok(customerDtos);
        }
    }

    @Override
    public ResponseEntity<Page<Map<String,Object>>> fillAllBy_Bill(CustomerParam2 param2) {
        if(param2.getKnown().equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, param2.getField());
            Pageable pageable = PageRequest.of(param2.getPage(), 10, sort);
            return  ResponseEntity.ok(repository.findCustomer_Bill(param2,pageable));
        }else{
            System.out.println(param2.getField());
            Sort sort =Sort.by(Sort.Direction.DESC, param2.getField());
            Pageable pageable = PageRequest.of(param2.getPage(), 10, sort);
            return  ResponseEntity.ok(repository.findCustomer_Bill(param2, pageable));
        }
    }
}
