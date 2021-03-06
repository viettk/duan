package com.demo.duan.service.staff;

import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.param.StaffParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface StaffService {
    ResponseEntity<Page<StaffDto>>getStaff(Pageable pageable);

    ResponseEntity<StaffDto> createStaff(StaffInput input);

    ResponseEntity<StaffDto>updateStaff(Integer id, StaffInput input);

    ResponseEntity<StaffDto>disableStaff(Integer id);

    ResponseEntity<Page<StaffDto>>searchByParam(StaffParam param, Pageable pageable);

    ResponseEntity<StaffDto>getByUsername(String username);

    ResponseEntity<StaffDto>resetPassord(Integer id, StaffInput input);
}
