package com.demo.duan.controller.thongke;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/thong-ke")
@AllArgsConstructor
public class ThongKeRest {
    private final BillService service;

    private final ProductService productService;

    @GetMapping("/donhuy")
    public Object donhanghuy(Integer month, Integer year){
        return  service.getDonHuy(month, year);
    }

    @GetMapping("/dontra")
    public Object donhangtra(Integer month, Integer year){
        return  service.getDonTra(month, year);
    }

    @GetMapping("/dontc")
    public Object donhangtc(Integer month, Integer year){
        return  service.getDonTc(month, year);
    }

    @GetMapping
    public List<Integer> getThongke(Integer month, Integer year){
        List<Integer> lst = new ArrayList<>();
        Integer huy = service.getDonHuy(month, year);
        Integer tra = service.getDonTra(month, year);
        Integer tc = service.getDonTc(month, year);
        lst.add(huy);
        lst.add(tra);
        lst.add(tc);
        return lst;
    }

    @GetMapping("/top5spbanchay")
    public ResponseEntity<List<ProductDto>> getTop5spBanChayTheoTime(Integer month, Integer year){
        return productService.Thongketop5spbanchayTheoTime(month, year);
    }

    @GetMapping("/soluongtop5banchay")
    public List<Integer> soLuongBan5spBanChay(Integer month, Integer year){
        return productService.soLuongBan5spBanChay(month, year);
    }

    @GetMapping("/doanhthu1nam")
    public List<BigDecimal> thongkedoanhthu(Integer year){
        return service.thongkedoanhthu(year);
    }
}
