package com.demo.duan.service.bill;

import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.BillEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.mapper.BillMapper;
import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import com.demo.duan.service.staff.dto.StaffDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService{

    private final BillRepository repository;

    private  final BillMapper mapper;

    private final BillDetailService billDetailService;

    private final CartDetailRepository cartDetailRepository;

    @Override
    @Transactional
    public ResponseEntity<BillDto> createByCustomer(BillInput input) {
        System.out.println(input.getCity());
        BillEntity entity = mapper.inputToEntity(input);
        entity.setStatus_order("Chờ xác nhận");
        repository.save(entity);

        System.out.println("0");
        BillDetailInput billDetailInput = new BillDetailInput();
        billDetailInput.setBillId(entity.getId());

        System.out.println("1");

        /* Dựa vào login để lấy thông tin khách hàng -> lấy cartId */
        billDetailService.createByCustomer(billDetailInput, 1);

        System.out.println("2");
        /* Xóa giỏ hàng */
        cartDetailRepository.deleteAllByCart_Id(1);

        System.out.println("3");
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<List<BillDto>> getStatus() {
        List<BillEntity> list = this.repository.all("Chờ xác nhận");
        long thoigiam = 1000 * 60 * 60 * 24;
        Date day = new Date();

        list.forEach(

                b->{
                    if ((day.getTime() - b.getCreate_date().getTime()) >= thoigiam)
                        b.setStatus_order("Đang chuẩn bị");
                        b.setUpdate_date(day);
                        repository.save(b);
                    System.out.println("Thành công kiểm tra trong data");
                }
        );
        return ResponseEntity.ok().body(mapper.EntitiesToDtos(list));
    }

    @Override
    public Integer getMonth(Integer month , Integer year)  {
        Integer view = repository.Thongke(month , year);
        if(view <= 0){
            view = 0;
        }
        System.out.println(view);
        return view;

    }

    @Override
    public Double getdoanhthu(Integer month) {
        Double viewdt = repository.Thongkedoanhthu(month);
        return viewdt;
    }

    @Override
    public ResponseEntity<Object>  getThongkespbanchay(Integer month) {
        Object viewsp = repository.Thongkespbanchay(month);
        return ResponseEntity.ok().body(viewsp);
    }

    @Override
    public ResponseEntity<List<Object>> getThongketop5spbanchay() {
        List<Object> viewsp = repository.Thongketop5spbanchay();
        System.out.println("ok");
        return ResponseEntity.ok().body(viewsp);
    }

    @Override
    public Integer getdonhang() {
        Integer viewdonhang = repository.Thongkedonhang();
        return viewdonhang;
    }

    @Override
    public ResponseEntity<List<Object>> getkhachhangmuanhiennhat(Integer month) {
//        List<Object> viewkh=  repository.thongkekhachhang(month);
//        return ResponseEntity.ok().body(viewkh);
        return  null;
    }


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
            id_code ="HD" + localDate.getMonthValue()+"-"+localDate.getYear()+"-"+num;
        }
        return id_code;
    }
    @Override
    public ResponseEntity<Page<BillDto>> getAll(Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known) {
        if (known.equals("up")){
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<BillDto> result = this.repository.findAll(pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }else {
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<BillDto> result = this.repository.findAll(pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }
    }

    @Override
    public ResponseEntity<BillDto> getOne(Integer id) {
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn này không tồn tại"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<Page<BillDto>> getByEmail(String email, Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known) {
        if (known.equals("up")){
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<BillDto> result = this.repository.findByEmail(email, pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }else {
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            Page<BillDto> result = this.repository.findByEmail(email, pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
        }
    }

    @Override
    public ResponseEntity<BillDto> update(BillInput input, Integer id) throws RuntimeException{
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Không có hóa đơn này"));
        this.mapper.inputToEntity(input, entity);
        Date date = new Date();
        entity.setUpdate_date(date);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }


}
