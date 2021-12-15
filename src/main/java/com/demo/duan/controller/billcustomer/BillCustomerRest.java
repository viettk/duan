package com.demo.duan.controller.billcustomer;

import com.demo.duan.entity.LocalStorageBillDetail;
import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
@AllArgsConstructor
public class BillCustomerRest {

    private final BillService service;

    private final BillDetailService billDetailService;

    @PostMapping("/dat/{id}")
    public ResponseEntity<BillDto> createByCustomer(
            @PathVariable Integer id ,
            @Valid @RequestBody BillInput input){
        System.out.println("cnjgo");
        return service.createByCustomer(id,input);
    }

    @PostMapping("/dat")
    public ResponseEntity<BillDto> createByCustomerNotLogin(
            @Valid @RequestBody BillInput input){
        return service.createByCustomerNotLogin(input);
    }

    @PostMapping("/detail/date/{id}")
    public  ResponseEntity<List<BillDetailDto>> create(
            @PathVariable Integer id,
            @RequestBody List<LocalStorageBillDetail> lstdto
            ){
        return billDetailService.createByCustomerNotLogin(id, lstdto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> updateByCustomer(@PathVariable Integer id, @Valid @RequestBody BillInput input){
        return service.updateByCustomer(id, input);
    }

    @GetMapping("/dathangthanhcong/{id}")
    public ResponseEntity<BillDto> getHoadonDatThanhCong(@PathVariable Integer id){
        return service.getOne(id);
    }

    @GetMapping("/tongcannag/{id}")
    public Float getAllWeight(@PathVariable Integer id){
        return billDetailService.getAllWeight(id);
    }

    @GetMapping("/tongtienhang/{id}")
    public BigDecimal getTotalBillDetail(@PathVariable Integer id){
        return billDetailService.getTotalBillDetail(id);
    }

    @PutMapping("/changeStatus_pay/{id}")
    public ResponseEntity<BillDto> changeStatus_pay(@PathVariable Integer id, String status_pay){
        return service.changeStatus_pay(id, status_pay);
    }

}
