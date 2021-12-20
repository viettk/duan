package com.demo.duan.service.thongke.khachhang;

import com.demo.duan.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ThongKeCustomerServiceImpl implements ThongkeCustomerService{
    private final CustomerRepository repository;


    @Override
    @Transactional
    public ResponseEntity<Page<Object>> findkhachhang( String known, String field, Integer page) {
        Pageable pageable = PageRequest.of(page, 7);
        Page<Object> objects = repository.findAllCustomerBought(pageable);
        if(known.equals("up") && field.equals("dh")){
            Page<Object> dto = repository.findAllCustomerBought(pageable);
            return ResponseEntity.ok().body(dto);
        } else if(known.equals("down") && field.equals("dh")){

        } else if(known.equals("up") && field.equals("total")){

        } else {

        }
        return null;
    }
}
