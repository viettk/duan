package com.demo.duan.service.staff;

import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface StaffService {
    ResponseEntity<Page<StaffDto>>getStaff(Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known);

    ResponseEntity<StaffDto>createStaff(StaffInput input);

    ResponseEntity<StaffDto>updateStaff(Integer id, StaffInput input);

}
