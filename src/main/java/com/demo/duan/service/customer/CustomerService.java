package com.demo.duan.service.customer;

import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.customer.input.CustomerInput;
import com.demo.duan.service.customer.param.CustomerParam2;
import com.demo.duan.service.customer.paramcustomer.Customerparam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface CustomerService {
    /* Khách hàng đăng kí */
    public ResponseEntity<Object> create(CustomerInput input);

    /* Khách hàng đổi mật khẩu */
    public ResponseEntity<CustomerDto> update(CustomerInput input);

    /* Lấy Email của khách hàng -> đổi lại mật khẩu */
    public ResponseEntity<CustomerDto> getEmail(String email);

    public ResponseEntity<Page<CustomerDto>> getAll(Customerparam param, Optional<Integer> limit, Optional<Integer> page);
    public ResponseEntity<CustomerDto> updateStatus(Integer id);

    /* ẩn hiện khách hàng */
    public ResponseEntity<Void> disable(Integer id);

    /* tìm khách hàng */
    ResponseEntity<Page<CustomerDto>> fillAll(String email, Boolean status, String known,String field,String name, Integer page);

    ResponseEntity<Page<Map<String,Object>>> fillAllBy_Bill(CustomerParam2 param2);

}
