package com.demo.duan.service.thongke.khachhang;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ThongkeCustomerService {
    public ResponseEntity<Page<Object>> findkhachhang(String known, String field, Integer page);

    public ResponseEntity<Page<Map<String,Object>>> findCustomerKhachHang(String opena, String enda, String known, String field, Integer page);
}
