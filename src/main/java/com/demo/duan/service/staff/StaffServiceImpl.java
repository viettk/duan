package com.demo.duan.service.staff;

import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.mapper.StaffMapper;
import com.demo.duan.service.staff.param.StaffParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService{
    private final StaffRepository repository;
    private final StaffMapper mapper;

    @Override
    @Transactional
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
    @Transactional
    public ResponseEntity<StaffDto> createStaff(StaffInput input) throws RuntimeException{
        if (!repository.findByEmail(input.getEmail()).isEmpty()){
            throw new RuntimeException("Email đã tồn tại");
        }
        if (!repository.findByPhone(input.getPhone()).isEmpty()){
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }
        StaffEntity entity = this.mapper.inputToEntity(input);
        this.mapper.inputToEntity(input);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<StaffDto> updateStaff(Integer id, StaffInput input) throws RuntimeException {
        StaffEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại hoăc không còn hoạt động"));
        this.mapper.inputToEntity(input, entity);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<StaffDto> disableStaff(Integer id) {
        StaffEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Không có nhân viên này"));
        if(entity.isStatus() == true){
            entity.setStatus(false);
        }
        else {
            entity.setStatus(true);
        }
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<Page<StaffDto>> searchByParam(StaffParam param, Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known) {
        if (known.equals("up")){
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<StaffDto> result = this.repository.searchByParam(param, pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }else {
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<StaffDto> result = this.repository.searchByParam(param, pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<StaffDto> getByUsername(String username) throws RuntimeException{
        StaffEntity entity = this.repository.findByEmail(username).orElseThrow(() -> new RuntimeException("Không tồn tại nhân viên này"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }
}
