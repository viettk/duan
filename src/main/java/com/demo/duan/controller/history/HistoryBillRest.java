package com.demo.duan.controller.history;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lich-su-mua-hang")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class HistoryBillRest {

    private final BillService service;

    @GetMapping
    public ResponseEntity<Page<BillDto>> getBillCustomer(
            String email,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page){
        return service.getCustomerId(email, page, limit);
    }

    @GetMapping("/detail")
    ResponseEntity<List<BillDetailDto>> getBillDetailCustomer(Integer id){
        return service.getBillDetailCustomer(id);
    }
}
