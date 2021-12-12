package com.demo.duan.service.vnpay;

import com.demo.duan.service.bill.dto.BillDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface VNPayService {
    public ResponseEntity<Object> pay(HttpServletRequest request, BillDto dto) throws IOException;
    public Map<String, Object> find(String time, Integer id , String ip) throws IOException;
    public ResponseEntity<Object>  refund(HttpServletRequest request) throws IOException;
    public ResponseEntity<Object>  check(HttpServletRequest request) throws IOException;
}