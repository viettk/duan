package com.demo.duan.service.customer;

import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CustomerService {
    /* Khách hàng đăng kí */
    public ResponseEntity<CustomerDto> create(CustomerInput input);

    /* Khách hàng đổi mật khẩu */
    public ResponseEntity<CustomerDto> update(CustomerInput input);

    /* Lấy Email của khách hàng -> đổi lại mật khẩu */
    public ResponseEntity<CustomerDto> getEmail(String email);
    public ResponseEntity<Page<CustomerDto>> getAll(Pageable pageable , Optional<Integer> limit, Optional<Integer> page);
    public ResponseEntity<CustomerDto> updateStatus(Integer id);
}
