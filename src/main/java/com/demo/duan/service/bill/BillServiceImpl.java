package com.demo.duan.service.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.mapper.BillMapper;
import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

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
    public ResponseEntity<Page<BillDto>> getAll(Pageable pageable) {
        Page <BillDto> result = this.repository.findAll(pageable).map(mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<BillDto> getOne(Integer id) {
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("id không tồn tại!"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<Page<BillDto>> getByEmail(String email, Pageable pageable) {
        Page<BillDto> result = this.repository.findByEmail(email,pageable).map(mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
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
