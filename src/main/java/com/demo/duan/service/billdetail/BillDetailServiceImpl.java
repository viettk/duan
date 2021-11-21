package com.demo.duan.service.billdetail;

import com.demo.duan.entity.*;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.billdetail.BillDetailRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import com.demo.duan.service.billdetail.mapper.BillDetailMapper;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.mapper.CartDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BillDetailServiceImpl implements BillDetailService{

    private final BillDetailRepository repository;

    private final BillDetailMapper mapper;

    private final CartDetailRepository cartDetailRepository;

    private final CartDetailMapper cartDetailMapper;

    private final BillRepository billRepository;

    @Override
    @Transactional
    public ResponseEntity<List<BillDetailDto>> createByCustomer(BillDetailInput input, Integer cartId) {
        List<CartDetailEntity> lstCartDetail = cartDetailRepository.findListByCartId(cartId);

        for(CartDetailEntity  x : lstCartDetail){
            input.setNumber(x.getNumber());
            input.setPrice(x.getProduct().getPrice());

            BillEntity billEntity = billRepository.getById(input.getBillId());
            BillDetailEntity entity = mapper.inputToEntity(input);
            entity.setProduct(x.getProduct());
            entity.setBill(billEntity);
            repository.save(entity);
        }
//        repository.saveAll(lst);
        List<BillDetailEntity> lst = new ArrayList<>();
        List<BillDetailDto> lstDto = mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(lstDto);
    }

    @Override
    @Transactional
    public ResponseEntity<List<BillDetailDto>> createByCustomerNotLogin(Integer id, List<LocalStorageBillDetail> inputs) {
        for(LocalStorageBillDetail x : inputs){
            BillDetailInput input = new BillDetailInput();
            input.setNumber(x.getNumber());
            input.setPrice(x.getPrice());
            BillEntity billEntity = billRepository.getById(id);

            BillDetailEntity entity = mapper.inputToEntity(input);

            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(x.getProduct_id());
            entity.setProduct(productEntity);

            entity.setBill(billEntity);
            entity.setTotal(x.getTotal());
            repository.save(entity);

            BigDecimal totalOfBill =  totalOfBill(billEntity.getId());
            billEntity.setTotal(totalOfBill);
            billRepository.save(billEntity);
        }
        List<BillDetailEntity> lst = new ArrayList<>();
        List<BillDetailDto> lstDto = mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(lstDto);
    }

    @Override
    @Transactional
    public BigDecimal totalOfBill(Integer billId) {
        BigDecimal sum = repository.totalOfBill(billId);
        return sum;
    }
}
