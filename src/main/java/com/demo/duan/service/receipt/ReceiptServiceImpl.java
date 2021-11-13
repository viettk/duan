package com.demo.duan.service.receipt;

import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.receipt.ReceiptRepository;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.receipt.dto.ReceiptDto;
import com.demo.duan.service.receipt.input.ReceiptInput;
import com.demo.duan.service.receipt.mapper.ReceiptMapper;
import com.demo.duan.service.receipt.param.ReceiptParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
@AllArgsConstructor
public class  ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository repository;
    private final ReceiptMapper mapper;
    private  final StaffRepository staffRepository;
    /* xem phiếu nhập */
    @Override
    @Transactional
    public ResponseEntity<Page<ReceiptDto>> getAll(Integer month, Integer year, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ReceiptDto> dto = this.repository.searchByAdmin(month, year, pageable).map(mapper::entityToDto);

            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ReceiptDto> dto = this.repository.searchByAdmin(month, year, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ReceiptDto> dto = this.repository.searchByAdmin(month, year, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }
    /* xem phiếu nhập theo id */
    @Override
    @Transactional
    public ResponseEntity<ReceiptDto> getOne(Integer id) throws RuntimeException {
        ReceiptEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Id không tồn tại"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }
    /*tạo phiếu nhập*/
    @Override
    @Transactional
    public ResponseEntity<ReceiptDto> create(ReceiptInput input){
        StaffEntity staff = staffRepository.findById(input.getStaffId()).get();
        Date day_date = new Date();
        ReceiptEntity entity = mapper.inputToEntity(input);
        repository.saveAndFlush(entity);
        entity.setStaff(staff);
        entity.setCreate_date(day_date);
        entity.setId_code(createCodeId(entity.getId()));
        entity.setTotal(BigDecimal.ZERO);
        repository.save(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }
    /* cập nhật phiếu nhập */
    @Override
    @Transactional
    public ResponseEntity<ReceiptDto> update(Integer id, ReceiptInput input) {
        Date day_date = new Date();
        StaffEntity staff = staffRepository.findById(input.getStaffId()).get();
        ReceiptEntity entity = repository.getById(id);

        // tạo thời gian
        long millisIn48Hours = 1000 * 60 * 60 * 48 ;

        // kiểm tra thời gian xem đã quá 48h chưa
        if (day_date.getTime() < entity.getCreate_date().getTime() +millisIn48Hours){
            entity.setStaff(staff);
            entity.setCreate_date(day_date);
            mapper.inputToEntity(input , entity);
            repository.save(entity);
        }else {
            throw new RuntimeException("Đã quá thời gian!");
        }
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<ReceiptDto> findByDate(Date date)throws RuntimeException {
        ReceiptEntity entity = repository.findByDate(date).orElseThrow(() -> new RuntimeException("Ngay khong ton tai"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

//    @Override
//    public ResponseEntity <Page<ReceiptDto>> findDate(Date getStartOfDay, Date getEndOfDay) {
//        Page<ReceiptDto> result = this.repository.findDate(getEndOfDay , getStartOfDay).map(mapper :: entityToDto);
//        return ResponseEntity.ok().body(result);
//    }

    @Scheduled(cron="0 0 0 1 * ?")
    public void reloadId(int num){
        num= 1;
    }
    /* tao id_code */
    private String createCodeId(Integer id_count){
        int num = 0;
        reloadId(num);
        String id_code = "";
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for(num = 1; num < id_count; num++){
            id_code ="PN" + localDate.getMonthValue()+"-"+localDate.getYear()+"-"+num;
        }
        return id_code;
    }
}
