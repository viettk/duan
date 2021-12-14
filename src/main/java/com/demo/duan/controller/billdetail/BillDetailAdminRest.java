package com.demo.duan.controller.billdetail;

import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/bill-detail")
public class BillDetailAdminRest {
    private final BillDetailService service;

    @GetMapping("/bill/{id}")
    public ResponseEntity<List<BillDetailDto>> getByBillId(
            @PathVariable("id") Integer idBill,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getByBill(idBill,field,known);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDetailDto>getById(@PathVariable("id") Integer id){
        return this.service.getById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillDetailDto>updateBillDetail(@PathVariable("id") Integer id, @RequestBody BillDetailInput input){
        return this.service.updateBillDetail(id, input);
    }
}
