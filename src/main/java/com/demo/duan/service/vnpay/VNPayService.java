package com.demo.duan.service.vnpay;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface VNPayService {
    public ResponseEntity<Object> pay() throws IOException;
    public ResponseEntity<Object> check() throws IOException;
    public ResponseEntity<Object>  refund() throws IOException;
}
