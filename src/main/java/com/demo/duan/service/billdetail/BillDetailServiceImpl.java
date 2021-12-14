package com.demo.duan.service.billdetail;

import com.demo.duan.entity.*;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.billdetail.BillDetailRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.bill.BillService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import com.demo.duan.service.billdetail.mapper.BillDetailMapper;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.mapper.CartDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    private final BillRepository billRepository;

    private final ProductRepository productRepository;

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
            entity.setTotal(BigDecimal.valueOf(x.getNumber()).multiply(x.getProduct().getPrice()) );
            repository.save(entity);

            ProductEntity productEntity = productRepository.getById(x.getProduct().getId());
            productEntity.setNumber(productEntity.getNumber() - x.getNumber() );;
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
            ProductEntity productEntity = productRepository.getById(x.getProduct_id());

            BillDetailInput input = new BillDetailInput();
            input.setNumber(x.getNumber());
            input.setPrice(x.getPrice());
            BillEntity billEntity = billRepository.getById(id);

            BillDetailEntity entity = mapper.inputToEntity(input);

            entity.setProduct(productEntity);
            productEntity.setNumber(productEntity.getNumber() - x.getNumber());

            entity.setBill(billEntity);
            entity.setTotal(x.getTotal());
            repository.save(entity);
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

    /* bill admin*/
    @Override
    @Transactional
    public ResponseEntity<List<BillDetailDto>> getAllAfterOrder(Integer billId) {
        List<BillDetailEntity> entities = repository.getListByCustomer(billId);
        List<BillDetailDto> billDetailDtos = mapper.EntitiesToDtos(entities);
        return ResponseEntity.ok().body(billDetailDtos);
    }

    @Override
    @Transactional
    public Float getAllWeight(Integer billId) {
        Float sumWeight = repository.tinhTongCanNangCart(billId);
        return sumWeight;
    }

    @Override
    @Transactional
    public BigDecimal getTotalBillDetail(Integer billId) {
        BigDecimal sumPrice = repository.tinhTongPrice(billId);
        return sumPrice;
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
        int returnNumber = entity.getNumber() - input.getNumber();
        BigDecimal returnPrice = entity.getPrice().multiply( new BigDecimal(returnNumber));
        BigDecimal totalNew = entity.getPrice().multiply(new BigDecimal(input.getNumber()));
        this.mapper.inputToEntity(input, entity);
        entity.setTotal(totalNew);
        this.repository.save(entity);
        //
        BillEntity billEntity = billRepository.findById(entity.getBill().getId()).orElseThrow( () -> new RuntimeException("Không thấy Hóa đơn này"));
        BigDecimal totalBill= billEntity.getTotal().subtract(entity.getPrice().multiply(returnPrice));
        billEntity.setTotal(totalBill);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<BillDetailDto> getById(Integer id) throws RuntimeException{
        BillDetailEntity entity = this.repository.findById(id).orElseThrow( () -> new RuntimeException("không tồn tại chi tiết hóa đơn này"));
        System.out.println(entity.getId());
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }
}
