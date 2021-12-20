package com.demo.duan.service.thongke.doanhthu;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.mapper.BillMapper;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.param.CategoryParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoanhThuServiceImpl implements DoanhThuService{

    private final BillRepository repository;

    private final BillMapper mapper;

    @Override
    @Transactional
    public ResponseEntity<Page<Object>> doanhthu(String opena, String enda, String known, String field, Integer page) {
        LocalDate open = LocalDate.parse(opena);
        LocalDate end = LocalDate.parse(enda);

        if(known.equals("up") && field.equals("total")){
            Pageable pageable = PageRequest.of(page, 7);
            Page<Object> dto = repository.soSpBandc1(open, end, pageable);
            return ResponseEntity.ok().body(dto);
        } else if(known.equals("down") && field.equals("total")){
            Pageable pageable = PageRequest.of(page, 7);
            Page<Object> dto = repository.soSpBandc2(open, end, pageable);
            return ResponseEntity.ok().body(dto);
        } else if(known.equals("up") && field.equals("day")){
            Pageable pageable = PageRequest.of(page, 7);
            Page<Object> dto = repository.soSpBandc3(open, end, pageable);
            return ResponseEntity.ok().body(dto);
        } else{
            Pageable pageable = PageRequest.of(page, 7);
            Page<Object> dto = repository.soSpBandc4(open, end, pageable);
            return ResponseEntity.ok().body(dto);
        }
    }

//    @Override
//    @Transactional
//    public ResponseEntity<Page<Object>> doanhthu(
//            Integer month, Integer year,
//            String known, String field,
//            Integer page) {
//        LocalDate initial = LocalDate.of(year, month, 13);
//        if(known.equals("up") && field.equals("total")){
//            LocalDate open = LocalDate.parse(year +"-"+ month + "-01");
//            LocalDate end = LocalDate.parse(year +"-"+ month+"-" + initial.lengthOfMonth());
//            Pageable pageable = PageRequest.of(page, 7);
//            Page<Object> dto = repository.soSpBandc1(open, end, pageable);
//            return ResponseEntity.ok().body(dto);
//        } else if(known.equals("down") && field.equals("total")){
//            LocalDate open = LocalDate.parse(year +"-"+ month + "-01");
//            LocalDate end = LocalDate.parse(year +"-"+ month+"-" + initial.lengthOfMonth());
//            Pageable pageable = PageRequest.of(page, 7);
//            Page<Object> dto = repository.soSpBandc2(open, end, pageable);
//            return ResponseEntity.ok().body(dto);
//        } else if(known.equals("up") && field.equals("day")){
//            LocalDate open = LocalDate.parse(year +"-"+ month + "-01");
//            LocalDate end = LocalDate.parse(year +"-"+ month+"-" + initial.lengthOfMonth());
//            Pageable pageable = PageRequest.of(page, 7);
//            Page<Object> dto = repository.soSpBandc3(open, end, pageable);
//            return ResponseEntity.ok().body(dto);
//        } else{
//            LocalDate open = LocalDate.parse(year +"-"+ month + "-01");
//            LocalDate end = LocalDate.parse(year +"-"+ month+"-" + initial.lengthOfMonth());
//            Pageable pageable = PageRequest.of(page, 7);
//            Page<Object> dto = repository.soSpBandc4(open, end, pageable);
//            return ResponseEntity.ok().body(dto);
//        }
//    }
}
