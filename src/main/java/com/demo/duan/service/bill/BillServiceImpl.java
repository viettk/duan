package com.demo.duan.service.bill;

import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.billdetail.BillDetailRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.mapper.BillMapper;
import com.demo.duan.service.bill.param.BillParam;
import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService{

    private final BillRepository repository;

    private  final BillMapper mapper;

    private final BillDetailService billDetailService;

    private final BillDetailRepository billDetailRepository;

    private final ProductRepository productRepository;

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
    public ResponseEntity<BillDto> getOne(Integer id) {
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn này không tồn tại"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<Page<BillDto>> getByEmail(String email, BillParam param, Pageable pageable) {
            Page<BillDto> result = this.repository.findByEmail(email, param, pageable).map(mapper :: entityToDto);
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

	@Override
	public ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input) throws RuntimeException{
		BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("Đơn hàng này không tồn tại!"));

		String status = "";
		
		Date date = new Date();
		switch (input.getStatus_order()){
	        case "Đã xác nhận":
	            status = "Đã xác nhận";
	            break;
	        case "Đang chuẩn bị hàng":
	            status = "Đang chuẩn bị hàng";
	            break;
	        case "Đang giao hàng":
	            status = "Đang giao hàng";
	            break;
	        case "Hoàn thành":
	            status = "Hoàn thành";
	            break;
            case "Thất bại":
                status = "Thất bại";
                break;
            case "Đã hủy":
                status = "Đã hủy";
                break;
            case "Giao hàng thành công":
                status = "Giao hàng thành công";
                break;
            case "Đơn hoàn trả":
                status = "Đơn hoàn trả";
                break;
	        default:
	            throw new RuntimeException("Không có trạng thái này, vui lòng cập nhật lại");
	    }
		String status_pay = "";
		if(input.getStatus_pay().equals("")) {
			status_pay = entity.getStatus_pay();
		}else {
			status_pay = input.getStatus_pay();
		}
		entity.setStatus_pay(status_pay);
		entity.setStatus_order(status);
		entity.setUpdate_date(date);
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}
	@Override
	public ResponseEntity<BillDto> updateStatusPay(Integer id, BillInput input) {
		BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("Đơn hàng này không tồn tại!"));
        String status = "";
        Date date = new Date();
        switch (input.getStatus_order()){
            case "Đã thanh toán":
                status = "Đã thanh toán";
                break;
            case "Đã hoàn trả":
                status = "Đã hoàn trả";
                break;
            case "Chưa thanh toán":
                status = "Chưa thanh toán";
                break;
            case "Hủy":
                status = "Thanh toán online";
                break;
            default:
                throw new RuntimeException("Không có trạng thái này, vui lòng cập nhật lại");
        }
		entity.setUpdate_date(date);
		entity.setStatus_pay(status);
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}

    @Override
    public ResponseEntity<Page<BillDto>> filterBill(BillParam param, Pageable pageable) {
        Page<BillDto> result = repository.filterBill(param, pageable).map( mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
    }

}
