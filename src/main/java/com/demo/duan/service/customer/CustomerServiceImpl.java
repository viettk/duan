package com.demo.duan.service.customer;

import com.demo.duan.config.hashpassword.HashPass;
import com.demo.duan.entity.CartEntity;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.repository.cart.CartRepository;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import com.demo.duan.service.customer.param.CustomerMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public ResponseEntity<CustomerDto> create(CustomerInput input) {
        /* Kiểm tra email đã tồn tại hay chưa */
        Integer count = repository.countAllByEmail(input.getEmail());
        if(count > 0){
            throw new RuntimeException("Email đã tồn tại");
        }

        /* Kiểm tra nhập lại mật khẩu */
        if(!input.getPassword().equals(input.getRepeatPassword())){
            throw new RuntimeException("Mật khẩu không khớp nhau");
        }

        /* Xác thực địa chỉ Email(không đầy đủ: chỉ kiểm tra tên miền) */
        boolean valid = EmailValidator.getInstance().isValid(input.getEmail());
        if(valid == false){
            throw new RuntimeException("Email không tồn tại");
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
}
