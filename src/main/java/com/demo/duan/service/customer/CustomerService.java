package com.demo.duan.service.customer;

import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import com.demo.duan.service.customer.paramcustomer.Customerparam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CustomerService {
    /* Khách hàng đăng kí */
    ResponseEntity<Object> create(CustomerInput input);

    /* Khách hàng đổi mật khẩu */
    ResponseEntity<CustomerDto> update(CustomerInput input);

    /* Lấy Email của khách hàng -> đổi lại mật khẩu */
    ResponseEntity<CustomerDto> getEmail(String email);

    ResponseEntity<Page<CustomerDto>> getAll(Customerparam param, Optional<Integer> limit, Optional<Integer> page);
    ResponseEntity<CustomerDto> updateStatus(Integer id);
}
