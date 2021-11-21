package com.demo.duan.service.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.ThongkeEntity;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import java.util.Date;
import java.util.List;

public interface BillService {
    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input);
    public ResponseEntity<List<BillDto>> getStatus();
    public Object getThongkespbanchay(Integer month);
    public Integer getMonth(Integer month , Integer year);
    public Double getdoanhthu(Integer month);
    public ResponseEntity<List<Object>> getThongketop5spbanchay();
    public Integer getdonhang();
    public ResponseEntity<List<Object>> getkhachhangmuanhiennhat(Integer month);
    public ResponseEntity<BillDto> createByCustomer(BillInput input);

    ResponseEntity<Page<BillDto>>getAll(Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

    ResponseEntity<BillDto>getOne(Integer id);

    ResponseEntity<Page<BillDto>>getByEmail(String name, Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

    ResponseEntity<BillDto>update(BillInput input, Integer id);

}
