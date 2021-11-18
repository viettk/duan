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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

	@Override
	public ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input) throws RuntimeException{
		BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("Đơn hàng này không tồn tại!"));
		String status = "";
		
		Date date = new Date();
		switch (input.getStatus_order()){
	        case "Xác nhận":
	            status = "Đã xác nhận";
	            break;
	        case "Hủy":
	            status = "Đã hủy";
	            break;
	        case "Chuẩn bị hàng":
	            status = "Đang chuẩn bị hàng";
	            break;
	        case "Giao hàng":
	            status = "Đang giao hàng";
	            break;
	        case "Hủy nhận hàng":
	            status = "Hủy nhận hàng";
	            break;
	        case "Đã nhận hàng":
	            status = "Giao hàng thành công";
	            break;
	        default:
	            throw new RuntimeException("Không có trạng thái này, vui lòng cập nhật lại");
	    }
		entity.setStatus_order(status);
		entity.setUpdate_date(date);
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}
	@Override
	public ResponseEntity<BillDto> updateStatusPay(Integer id) {
		BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("Đơn hàng này không tồn tại!"));
		Date date = new Date();
		if (entity.isStatus_pay()) {
			entity.setStatus_pay(false);
		}else {
			entity.setStatus_pay(true);
		}
		entity.setUpdate_date(date);
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}

    @Override
    public ResponseEntity<Page<BillDto>> getByStatus(boolean pay, String order, Optional<Integer> limit, Optional<Integer> page, Optional<String> field, String known) {
        Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
        Page<BillDto> result = this.repository.findByStatus(pay, order, pageable).map(mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
    }

}
