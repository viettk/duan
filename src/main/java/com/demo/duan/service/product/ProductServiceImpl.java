package com.demo.duan.service.product;

import com.demo.duan.entity.ProductEntity;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.mapper.ProductMapper;
import com.demo.duan.service.product.param.ProductParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchNewArrival() {
        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDto> dto = this.productRepository.searchNewArrival(pageable).map(mapper::entityToDto);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchBySHF() {
        Pageable pageable = PageRequest.of(0,4);
        Page<ProductDto> dto = this.productRepository.searchBySHF(pageable).map(mapper::entityToDto);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchByModelKit() {
        Pageable pageable = PageRequest.of(0,4);
        Page<ProductDto> dto = this.productRepository.searchByModelKit(pageable).map(mapper::entityToDto);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchByStaticModel() {
        Pageable pageable = PageRequest.of(0,4);
        Page<ProductDto> dto = this.productRepository.searchByStaticModel(pageable).map(mapper::entityToDto);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchByCategoryName(ProductParam param, Optional<String> field, String known) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(0, 15, sort);
            Page<ProductDto> dto = this.productRepository.searchByCategoryName(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(0, 15, sort);
            Page<ProductDto> dto = this.productRepository.searchByCategoryName(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ProductDto> getOne(Integer id) {
        /* Kiểm tra id của sản phẩm có tồn tại hay ko */
        ProductEntity entity = this.productRepository.findByIdAndStatusIsFalse(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
       return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }
}
