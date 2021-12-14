package com.demo.duan.controller.billdetail;

import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoa-don-chi-tiet-thanh-cong")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class BillDetailCustomerRest {

    private final BillDetailService service;

    @GetMapping("/{id}")
    public ResponseEntity<List<BillDetailDto>> getAllAfterOrder(@PathVariable Integer id){
        return service.getAllAfterOrder(id);
    }
}
