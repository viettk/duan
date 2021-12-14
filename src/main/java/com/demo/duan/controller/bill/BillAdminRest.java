package com.demo.duan.controller.bill;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.param.BillParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/bill")
public class BillAdminRest {
    private final BillService service;

    @GetMapping
    public ResponseEntity<Page<BillDto>>findAll(
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "pay", required = false) String pay,
            @RequestParam(value = "start", required = false) Date start,
            @RequestParam(value = "end", required = false) Date end,
            @RequestParam(value = "_limit", required = false) Optional<Integer> limit,
            @RequestParam(value = "_page", required = false) Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
//        BillParam param = new BillParam(order, pay, start, end);
//        if (known.isEmpty()){
//            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("create_date"));
//            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
//            return service.filterBill(param, pageable);
//        }else {
//            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
//            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
//            return service.filterBill(param, pageable);
//        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDto>getOne(@PathVariable("id") Integer id){
        return this.service.getOne(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Page<BillDto>>getByEmail(
            @PathVariable("email") String email,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "pay", required = false) String pay,
            @RequestParam(value = "start", required = false) Optional<Date> start,
            @RequestParam(value = "end", required = false) Optional<Date> end,
            @RequestParam(value = "_limit", required = false) Optional<Integer> limit,
            @RequestParam(value = "_page", required = false) Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
//        BillParam param = new BillParam(order, pay, start.orElse(null), end.orElse(null));
//        if (known.isEmpty()){
//            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("create_date"));
//            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
//            return service.getByEmail(email, param, pageable);
//        }else {
//            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
//            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
//            return service.getByEmail(email, param, pageable);
//        }
        return null;
    }


    @PutMapping("/{id}")
    public ResponseEntity<BillDto>updateBill(@PathVariable("id") Integer id, BillInput input){
        return this.service.update(input, id);
    }
    @PutMapping("/status-order/{id}")
    public ResponseEntity<BillDto>updateStatusOrder(
            @PathVariable("id") Integer id,
            @RequestBody BillInput input
    ){
        return this.service.updateStatusOder(id, input);
    }
    @PostMapping("/status-pay/{id}")
    public ResponseEntity<BillDto>updateStatusPay(
            @PathVariable("id") Integer id,
            @RequestBody BillInput input
    ){
        return this.service.updateStatusPay(id, input);
    }
}
