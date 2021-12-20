package com.demo.duan.controller.thongke;

import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.product.ProductService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.thongke.doanhthu.DoanhThuService;
import com.demo.duan.service.thongke.khachhang.ThongkeCustomerService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/thong-ke")
@AllArgsConstructor
public class ThongKeRest {
    private final BillService service;

    private final ProductService productService;

    private final DoanhThuService doanhThuService;

    private final ThongkeCustomerService thongkeCustomerService;

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

    @GetMapping("/choxacnhan")
    public Object choxacnhan(){
        return  service.getChoxacnhan();
    }

    @GetMapping("/dangchuanbi")
    public Object dangchuanbi(){
        return  service.getDangChuanbi();
    }

    @GetMapping("/danggiao")
    public Object danggiao(){
        return  service.getdangGiao();
    }

    @GetMapping("/tuchoi")
    public Object tuchoi(){
        return  service.tuchoi();
    }

    @GetMapping("/thatbai")
    public Object thatbai(){
        return  service.thatbai();
    }

    @GetMapping("/thanhcong")
    public Object thanhcong(){
        return  service.thanhcong();
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

    @GetMapping("/thongkeTypepay")
    public List<Integer> getTypePay(Integer month, Integer year){
        List<Integer> lst = new ArrayList<>();
        Integer cod = service.getCOD(month, year);
        Integer vnpay = service.getVNPAY(month, year);
        lst.add(cod);
        lst.add(vnpay);
        return  lst;
    }

    @GetMapping("/gettongadmin")
    public ResponseEntity<Page<Object>> doanhthu(
            String opena, String enda,
            String known, String field,
            Integer page
    ){
        return doanhThuService.doanhthu(opena, enda, known, field, page);
    }

    @GetMapping("/getAllkhachhang")
    public ResponseEntity<Page<Object>> khachhang(
            String known, String field,
            Integer page
    ){
        return thongkeCustomerService.findkhachhang(known, field, page);
    }
}
