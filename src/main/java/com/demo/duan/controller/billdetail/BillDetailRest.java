package com.demo.duan.controller.billdetail;

import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bill/billDetail")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class BilldetailRest {
    private final BillDetailService service;
    @PutMapping("/{id}")
    public ResponseEntity<BillDetailDto> update(@PathVariable Integer id , @Valid @RequestBody BillDetailInput input){
        return  service.update(input, id);
    }
    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id){
        service.deleteById(id);
    }
}
