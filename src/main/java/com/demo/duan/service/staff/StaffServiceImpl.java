package com.demo.duan.service.staff;

import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.mapper.StaffMapper;
import com.demo.duan.service.staff.param.StaffParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
@Service
@AllArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService, UserDetailsService {
    private final StaffRepository repository;
    private final StaffMapper mapper;

    // login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StaffEntity staff = repository.findByEmail(username).orElseThrow( () -> new UsernameNotFoundException("không tồn tại email này"));
        if (staff == null){
            log.error("Không tồn tại tài khoản này");
            throw new UsernameNotFoundException("Không tồn tại tài khoản này");
        } else {
            log.info("Tài khoản tồn tại: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(staff.getRole()));
        return new User(staff.getEmail(), staff.getPassword(), authorities);
    }

    // service
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
            System.out.println("Đã tồn tại email này");
            return ResponseEntity.badRequest().build();
        }
        if (!repository.findByPhone(input.getPhone()).isEmpty()){
            System.out.println("Đã tồn tại số điện thoại này");
            return ResponseEntity.badRequest().build();
        }
        StaffEntity entity = this.mapper.inputToEntity(input);
        this.mapper.inputToEntity(input);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<StaffDto> updateStaff(Integer id, StaffInput input) throws RuntimeException {
        StaffEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Không có nhân viên này"));
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
