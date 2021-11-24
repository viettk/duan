package com.demo.duan.service.receiptDetail;

import com.demo.duan.entity.CartEntity;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.entity.ReceiptDetailEntity;
import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.repository.receipt.ReceiptRepository;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.receipt.ReceiptService;
import com.demo.duan.repository.receiptdetail.ReceiptDetailRepository;
import com.demo.duan.service.receipt.param.ReceiptParam;
import com.demo.duan.service.receiptDetail.dto.ReceiptDetailDto;
import com.demo.duan.service.receiptDetail.input.ReceiptDetailInput;
import com.demo.duan.service.receiptDetail.mapper.ReceiptDetailMapper;
import com.demo.duan.service.receiptDetail.param.ReceiptDetailparam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceiptDetailServiceImpl implements ReceiptDetailService {
    private final ReceiptDetailRepository receiptDetailRepository;
    private final ReceiptDetailMapper mapper;
    private  final ReceiptRepository receiptRepository;
    private  final ProductRepository productRepository;
    private  final ReceiptService receiptService;

    @Override
    @Transactional
    public ResponseEntity<Page<ReceiptDetailDto>> searchByAdmin(Integer receiptid, ReceiptDetailparam param, Optional<String> known, Optional<String> field, Optional<Integer> limit, Optional<Integer> page) {
        if(known.get().equals("up")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ReceiptDetailDto> dto = receiptDetailRepository.searchByAdmin(receiptid, param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        } else{
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ReceiptDetailDto> dto = receiptDetailRepository.searchByAdmin(receiptid, param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ReceiptDetailDto> findByIdRecript(Integer receipt_id) throws RuntimeException {
        ReceiptDetailEntity entity = receiptDetailRepository.findByIdRecript(receipt_id).orElseThrow(() -> new RuntimeException("Id khong ton tai"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }


    @Override
    @Transactional
    public ResponseEntity<ReceiptDetailDto> create(ReceiptDetailInput input) {
        ReceiptEntity receipt = receiptRepository.findById(input.getReceiptId()).get();
        ProductEntity product = productRepository.getById(input.getProductId());

        int count = receiptDetailRepository.countAllByReceipt_IdAndProduct_Id(input.getReceiptId(), input.getProductId());
        if(count > 0){
//            throw new RuntimeException("Sản phẩm đã có trong phiếu nhập");
            ReceiptDetailEntity entity = receiptDetailRepository.findByReceipt_IdAndProduct_Id(input.getReceiptId(), input.getProductId());
            update(entity.getId(), input);
            totalOfCart(input.getReceiptId());
            return ResponseEntity.ok().body(mapper.entityToDto(entity));
        }

        // tính tống tiền số lượng * đơn giá
        else {
            BigDecimal total = input.getPrice().multiply(BigDecimal.valueOf(input.getNumber()));

            ReceiptDetailEntity receiptDetailEntity = mapper.inputToEntity(input);
            receiptDetailEntity.setReceipt(receipt);
            receiptDetailEntity.setProduct(product);
            receiptDetailEntity.setTotal(total);
            receiptDetailRepository.save(receiptDetailEntity);

            // cập nhập số lượng vào trong kho
            Integer saveNumber = input.getNumber() + product.getNumber();
            product.setNumber(saveNumber);
            productRepository.save(product);
            totalOfCart(input.getReceiptId());
            return ResponseEntity.ok().body(mapper.entityToDto(receiptDetailEntity));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ReceiptDetailDto> update(Integer id, ReceiptDetailInput input) {
        ReceiptDetailEntity receiptDetailEntity = receiptDetailRepository.getById(id);
        ProductEntity product = productRepository.getById(input.getProductId());
        // kiểm tra số lượng trong phiếu nhập chi tiết có khác với số lượng nhập trong ô input hay ko
        if (receiptDetailEntity.getNumber() != input.getNumber()){
            Integer saveNumber = product.getNumber() - receiptDetailEntity.getNumber()+ input.getNumber();
            product.setNumber(saveNumber);
            productRepository.save(product);
            totalOfCart(input.getReceiptId());
        }
        if (receiptDetailEntity.getProduct() != product){
            create(input);
            totalOfCart(input.getReceiptId());
        }else {
            receiptDetailEntity.setNumber(input.getNumber());
            receiptDetailEntity.setPrice(input.getPrice());
            BigDecimal total = input.getPrice().multiply(BigDecimal.valueOf(input.getNumber()));
            receiptDetailEntity.setTotal(total);
            receiptDetailRepository.save(receiptDetailEntity);
            totalOfCart(input.getReceiptId());
        }
        return ResponseEntity.ok().body(mapper.entityToDto(receiptDetailEntity));
    }

    @Override
    @Transactional
    public ResponseEntity<ReceiptDetailDto> deleteById(Integer id, Integer receiptId) {
        ReceiptDetailEntity entity = receiptDetailRepository.getById(id);
        receiptDetailRepository.delete(entity);
        totalOfCart(receiptId);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<ReceiptDetailDto> getId(Integer id) {
        ReceiptDetailEntity entity = receiptDetailRepository.getById(id);
        ReceiptDetailDto dto = mapper.entityToDto(entity);
        return ResponseEntity.ok().body(dto);
    }

    public BigDecimal totalOfCart(Integer cartId) {
        BigDecimal finalTotal = receiptDetailRepository.totalOfCart(cartId);
        ReceiptEntity receiptEntity = receiptRepository.getById(cartId);
        int count = receiptDetailRepository.countAllByReceipt_Id(cartId);
        if(count <= 0 ){
            finalTotal = BigDecimal.ZERO;;
        }
        receiptEntity.setTotal(finalTotal);
        return finalTotal;
    }
}
