package com.demo.duan.controller.thongke;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/thong-ke")
@AllArgsConstructor
public class ThongKeRest {
    private final BillService billService;

    @GetMapping("/doanh-thu")
    public ResponseEntity<BillDto> find(Integer month, Integer year){
        Date d = new Date();
        System.out.println(d);
        return null;
    }
}
