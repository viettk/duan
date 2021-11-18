package com.demo.duan.service.billdetail;

import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.CartDetailEntity;
import com.demo.duan.repository.billdetail.BillDetailRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import com.demo.duan.service.billdetail.mapper.BillDetailMapper;
import com.demo.duan.service.cartdetail.mapper.CartDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillDetailServiceImpl implements BillDetailService{

    private final BillDetailRepository repository;

    private final BillDetailMapper mapper;

    private final CartDetailRepository cartDetailRepository;

    private final CartDetailMapper cartDetailMapper;

    @Override
    public ResponseEntity<List<BillDetailDto>> createByCustomer(BillDetailInput input, Integer cartId) {
        List<CartDetailEntity> lstCartDetail = cartDetailRepository.findListByCartId(cartId);

        List<BillDetailEntity> lst = new ArrayList<>();
        for(CartDetailEntity  x : lstCartDetail){
            for(BillDetailEntity y: lst){
                y.setProduct(x.getProduct());
                y.setNumber(x.getNumber());
                y.setPrice(x.getProduct().getPrice());
            }
        }
        repository.saveAll(lst);
        List<BillDetailDto> lstDto = mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(lstDto);
    }

    @Override
    public ResponseEntity<List<BillDetailDto>> getByBill(Integer idBill, Optional<String> field, String known) {
        if (known.equals("up")){
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("id"));
            List<BillDetailEntity> result = this.repository.findByBill(idBill, sort);
            return ResponseEntity.ok().body(this.mapper.EntitiesToDtos(result));
        }
        else {
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("id"));
            List<BillDetailEntity> result = this.repository.findByBill(idBill, sort);
            return ResponseEntity.ok().body(this.mapper.EntitiesToDtos(result));
        }
    }

    @Override
    public ResponseEntity<BillDetailDto> updateBillDetail(Integer id, BillDetailInput input) throws RuntimeException{
        BillDetailEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Không có hóa đơn chi tiết này"));
        this.mapper.inputToEntity(input, entity);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<BillDetailDto> getById(Integer id) throws RuntimeException{
        BillDetailEntity entity = this.repository.findById(id).orElseThrow( () -> new RuntimeException("không tồn tại chi tiết hóa đơn này"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }
}
