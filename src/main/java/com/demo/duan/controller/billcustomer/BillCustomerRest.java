package com.demo.duan.controller.billcustomer;

import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.ThongkeEntity;
import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
@AllArgsConstructor
public class BillCustomerRest {

    private final BillService service;
    @GetMapping
    public ResponseEntity<List<BillDto>> getStatus(){
        return  service.getStatus();
    }
    @GetMapping("/thongke")
    public Integer thongke(Integer month , Integer year){
        return  service.getMonth(month , year);
    }
    @GetMapping("/donhangdahuy")
    public Integer donhanghuy(){
        return  service.getdonhang();
    }
    @GetMapping("/doanhthu")
    public Double doanhthu(Integer month){
        return  service.getdoanhthu(month);
    }
    @GetMapping("/top1")
    public Object thongketop1(Integer month){
        return  service.getThongkespbanchay(month);
    }
    @GetMapping("/top5")
    public ResponseEntity<List<Object>> thongketop5(){
        return  service.getThongketop5spbanchay();
    }
    @GetMapping("/khachhang")
    public ResponseEntity<List<Object>> thongkekhachhang(Integer month){
        return  service.getkhachhangmuanhiennhat(month);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillDto> updateByCustomer(@PathVariable Integer id, @Valid @RequestBody BillInput input){
        System.out.println("ok");
        return service.updateByCustomer(id, input);
    }

}
