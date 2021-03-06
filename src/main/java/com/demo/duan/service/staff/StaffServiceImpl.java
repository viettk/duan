package com.demo.duan.service.staff;

import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.mapper.StaffMapper;
import com.demo.duan.service.staff.param.StaffParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
	private final StaffRepository repository;
	private final StaffMapper mapper;

	@Override
	@Transactional
	public ResponseEntity<Page<StaffDto>> getStaff(Pageable pageable) {
		Page<StaffDto> result = this.repository.findAll(pageable).map(mapper::entityToDto);
		return ResponseEntity.ok().body(result);
	}

	@Override
    @Transactional
    public ResponseEntity<StaffDto> createStaff(StaffInput input){
    	Boolean email = repository.findByEmail(input.getEmail()).isEmpty();
    	Boolean phone = repository.findByPhone(input.getPhone()).isEmpty();
        if (!email) {
        	System.out.println("emial"+ email);
        	throw new RuntimeException("Email này đã tồn tại!");

        }
        else if (!phone) {
        	System.out.println("phone"+phone);
        	throw new RuntimeException("Số điện thoại này đã tồn tại!");
        }
    	System.out.println(repository.findByEmail(input.getEmail()).isEmpty() +"ok"+ repository.findByPhone(input.getPhone()).isEmpty());
        StaffEntity entity = this.mapper.inputToEntity(input);
        this.mapper.inputToEntity(input);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));

    }

	@Override
	@Transactional
	public ResponseEntity<StaffDto> updateStaff(Integer id, StaffInput input) throws RuntimeException {
		StaffEntity entity = this.repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tồn tại nhân viên này!"));
		String pw = entity.getPassword();
		this.mapper.inputToEntity(input, entity);
		entity.setPassword(pw);
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}

	@Override
	@Transactional
	public ResponseEntity<StaffDto> disableStaff(Integer id) {
		StaffEntity entity = this.repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tồn tại nhân viên này!"));
		if (entity.isStatus() == true) {
			entity.setStatus(false);
		} else {
			entity.setStatus(true);
		}
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}

	@Override
	@Transactional
	public ResponseEntity<Page<StaffDto>> searchByParam(StaffParam param, Pageable pageable) {
		Page<StaffDto> result = this.repository.filterByParam(param, pageable).map(mapper::entityToDto);
		return ResponseEntity.ok().body(result);
	}

	@Override
	@Transactional
	public ResponseEntity<StaffDto> getByUsername(String username) throws RuntimeException {
		StaffEntity entity = this.repository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Không tồn tại nhân viên này"));
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}

	@Override
	public ResponseEntity<StaffDto> resetPassord(Integer id, StaffInput input) throws RuntimeException {
		StaffEntity entity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("not found staff in the database!"));
		entity.setPassword(input.getPassword());
		repository.save(entity);
		return ResponseEntity.ok().body(mapper.entityToDto(entity));
	}
}
