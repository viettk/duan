package com.demo.duan.service.thongke.doanhthu;

import com.demo.duan.service.bill.dto.BillDto;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoanhThuService {
//    public ResponseEntity<Page<Object>> doanhthu(
//            Integer month, Integer year,
//            String known,
//            String field,
//            Integer page
//    );

    public ResponseEntity<Page<Object>> doanhthu(
            String opena, String enda,
            String known,
            String field,
            Integer page
    );
}
