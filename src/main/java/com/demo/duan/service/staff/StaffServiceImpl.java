package com.demo.duan.service.staff;

import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.mapper.StaffMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService{
    private final StaffRepository repository;
    private final StaffMapper mapper;

    @Override
    public ResponseEntity<Page<StaffDto>> getStaff(Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known) {
        if (known.equals("up")){
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<StaffDto> result = this.repository.findAll(pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }else {
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<StaffDto> result = this.repository.findAll(pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }
    }

    @Override
    public ResponseEntity<StaffDto> createStaff(StaffInput input) {
        StaffEntity entity = this.mapper.inputToEntity(input);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<StaffDto> updateStaff(Integer id, StaffInput input) throws RuntimeException {
        StaffEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Không có nhân viên này"));;
        this.mapper.inputToEntity(input, entity);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }
}
