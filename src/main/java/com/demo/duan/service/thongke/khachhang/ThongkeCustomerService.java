package com.demo.duan.service.thongke.khachhang;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ThongkeCustomerService {
    public ResponseEntity<Page<Object>> findkhachhang(String known, String field, Integer page);
}
