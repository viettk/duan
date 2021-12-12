package com.demo.duan.controller.pay;

import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.vnpay.PayEntity;
import com.demo.duan.service.vnpay.VNPayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/thanhtoan")
@CrossOrigin(origins = "*")
public class PayRest {
    private final VNPayService vnPayService;
    @PostMapping("/createUrl")
    public ResponseEntity<Object> createUrlPay(@RequestBody BillDto dto, HttpServletRequest request) throws IOException {
        return vnPayService.pay(request,dto);
    }
//    @GetMapping("/find")
//    public ResponseEntity<Object> checkOrder(HttpServletRequest request) throws IOException {
//        return vnPayService.find();
//    }
    @GetMapping("/checksum")
    public ResponseEntity<Object> check(HttpServletRequest request) throws IOException {
        return vnPayService.check(request);
    }
}
