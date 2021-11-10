package com.demo.duan.service.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.ThongkeEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.mapper.BillMapper;
import com.demo.duan.service.billdetail.BillDetailService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService{

    private final BillRepository repository;

    private  final BillMapper mapper;
    @Override
    @Transactional
    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input) {
        Date day = new Date();
        BillEntity entity = repository.getById(id);
        String status = entity.getStatus_order();
        switch (status){
            case "Chờ xác nhận":
                input.setStatus_order("Đang chuẩn bị");
                break;
            case "Đang chuẩn bị":
                input.setStatus_order("Đang giao");
                break;
            case "Đang giao":
                input.setStatus_order("Đã giao");
                break;
            case "Đã giao":
                input.setStatus_order("Giao thành công");
                break;
            default:
                throw new RuntimeException("Bạn không thể cập nhật Hóa đơn");
        }
        /* Cập nhật hóa đơn và lưu vào db */
        entity.setUpdate_date(day);
        mapper.inputToEntity(input, entity);

        repository.save(entity);
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
    public ResponseEntity<List<ThongkeEntity>> getMonth(Date startDate , Date endDate)  {
//        List<ThongkeEntity> view = repository.Thongke(startDate,endDate);
//        return ResponseEntity.ok().body(view);
        return null;
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
}
