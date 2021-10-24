package com.demo.duan.service.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.DiscountEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.repository.discount.DiscountRepository;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.mapper.BillMapper;
import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService{

    private final BillRepository repository;

    private  final BillMapper mapper;

    private final BillDetailService billDetailService;

    private final CartDetailRepository cartDetailRepository;

    private final DiscountRepository discountRepository;

    @Override
    @Transactional
    public ResponseEntity<BillDto> createByCustomer(BillInput input, String discountName) {

        Date date = new Date();

        /* Kiểm tra mã giảm giá có khả dụng hay ko */
        DiscountEntity discount = discountRepository.searchDiscountByCustomer(discountName)
                .orElseThrow(()->new RuntimeException("Mã Giảm giá không khả dụng"));

        /* lưu hóa đơn vào máy */
        BillEntity entity = mapper.inputToEntity(input);
        entity = repository.saveAndFlush(entity);
        entity.setStatus_order("Chờ xác nhận");
        entity.setCreate_date(date);
        entity.setUpdate_date(date);
        entity.setTotal(BigDecimal.ZERO);
        entity.setDiscount(discount);
        entity.setId_code(createCodeId(entity.getId()));
        repository.save(entity);

        /* tạo hóa đơn chi tiết */
        BillDetailInput billDetailInput = new BillDetailInput();
        billDetailInput.setBillId(entity.getId());


        /* Dựa vào login để lấy thông tin khách hàng -> lấy cartId */
        billDetailService.createByCustomer(billDetailInput, 1);

        /* Xóa giỏ hàng */
        cartDetailRepository.deleteAllByCart_Id(1);

        /* Trừ mã giảm giá */
        discount.setNumber(discount.getNumber() - 1);

        /* Nếu là lần đầu tiên khách đặt hàng thì sẽ lưu giá địa chỉ của khách  */

      return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input) {
        BillEntity entity = repository.getById(id);

        /* Nếu hóa đơn ở các trạng thái đang giao, giao thành công , Đã hủy thì ko cập nhật lại đc Hóa đơn */
        if(entity.getStatus_order().equals("Đang giao hàng") || entity.getStatus_order().equals("Giap thành công")
        || entity.getStatus_order().equals("Đã Hủy")){
            throw new RuntimeException("Bạn không thể cập nhật Hóa đơn");
        }

        /* Cập nhật hóa đơn và lưu vào db */
        mapper.inputToEntity(input, entity);
        repository.save(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
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
