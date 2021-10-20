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
    public ResponseEntity<BillDto> createByCustomer(BillInput input) {

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        /* Kiểm tra mã giảm giá có khả dụng hay ko */
        DiscountEntity discount = discountRepository.getById(1);

        /* lưu hóa đơn vào máy */
        BillEntity entity = mapper.inputToEntity(input);
        entity = repository.saveAndFlush(entity);
        entity.setStatus_order("Chờ xác nhận");
        entity.setCreate_date(date);
        entity.setUpdate_date(date);
        entity.setTotal(BigDecimal.ZERO);
        entity.setDiscount(discount);
        entity.setId_code("HD"+localDate.getDayOfMonth()+"-"+localDate.getMonthValue()+"-"+localDate.getYear()+"-"
        +createCodeId(entity.getId()));
        repository.save(entity);

        /* tạo hóa đơn chi tiết */
        BillDetailInput billDetailInput = new BillDetailInput();
        billDetailInput.setBillId(entity.getId());


        /* Dựa vào login để lấy thông tin khách hàng -> lấy cartId */
        billDetailService.createByCustomer(billDetailInput, 1);

        /* Xóa giỏ hàng */
        cartDetailRepository.deleteAllByCart_Id(1);

        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    private String createCodeId(Integer id_count){
        if (id_count < 10) {
            return "000" + id_count;
        } else if (id_count >= 10 && id_count < 1000) {
            return "00" + id_count;
        } else {
            return Long.toString(id_count);
        }
    }
}
