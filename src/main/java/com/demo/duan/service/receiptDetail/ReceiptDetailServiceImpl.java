package com.demo.duan.service.receiptDetail;

import com.demo.duan.entity.ProductEntity;
import com.demo.duan.entity.ReceiptDetailEntity;
import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.repository.receipt.ReceiptRepository;
import com.demo.duan.service.receipt.ReceiptService;
import com.demo.duan.repository.receiptdetail.ReceiptDetailRepository;
import com.demo.duan.service.receiptDetail.dto.ReceiptDetailDto;
import com.demo.duan.service.receiptDetail.input.ReceiptDetailInput;
import com.demo.duan.service.receiptDetail.mapper.ReceiptDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor

public class ReceiptDetailServiceImpl implements ReceiptDetailService {
    private final ReceiptDetailRepository receiptDetailRepository;
    private final ReceiptDetailMapper mapper;
    private  final ReceiptRepository receiptRepository;
    private  final ProductRepository productRepository;
    private  final ReceiptService receiptService;

    @Override
    public ResponseEntity<Page<ReceiptDetailDto>> getAll(Pageable pageable) {
        Page<ReceiptDetailDto> result = this.receiptDetailRepository.all(pageable).map(mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<ReceiptDetailDto> findByIdRecript(Integer receipt_id) throws RuntimeException {
        ReceiptDetailEntity entity = receiptDetailRepository.findByIdRecript(receipt_id).orElseThrow(() -> new RuntimeException("Id khong ton tai"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }


    @Override
    @Transactional
    public ResponseEntity<ReceiptDetailDto> create(ReceiptDetailInput input) {
        ReceiptEntity receipt = receiptRepository.findById(input.getReceiptId()).get();
        ProductEntity product = productRepository.getById(input.getProductId());
        // tính tống tiền số lượng * đơn giá
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
        return ResponseEntity.ok().body(mapper.entityToDto(receiptDetailEntity));
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
        }
        receiptDetailEntity.setNumber(input.getNumber());
        receiptDetailEntity.setPrice(input.getPrice());
        BigDecimal total = input.getPrice().multiply(BigDecimal.valueOf(input.getNumber()));
        receiptDetailEntity.setTotal(total);
        receiptDetailRepository.save(receiptDetailEntity);

        return ResponseEntity.ok().body(mapper.entityToDto(receiptDetailEntity));
    }
}
