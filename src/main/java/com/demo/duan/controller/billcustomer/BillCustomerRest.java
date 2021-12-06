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
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
@AllArgsConstructor
public class BillCustomerRest {

    private final BillService service;

    private final BillDetailService billDetailService;

    @PostMapping("/dat/{cartId}")
    public ResponseEntity<BillDto> createByCustomer(
            @PathVariable Integer cartId ,
            @Valid @RequestBody BillInput input){
        return service.createByCustomer(cartId,input);
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
}
