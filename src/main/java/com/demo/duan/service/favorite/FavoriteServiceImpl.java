package com.demo.duan.service.favorite;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.FavoriteEntity;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.favorite.FavoriteRepository;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.favorite.dto.FavoriteDto;
import com.demo.duan.service.favorite.input.FavoriteInput;
import com.demo.duan.service.favorite.mapper.FavoriteMapper;
import com.demo.duan.service.favorite.param.FavoriteParam;
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
public class FavoriteServiceImpl implements FavoriteService{

    private final FavoriteMapper mapper;

    private final FavoriteRepository repository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public ResponseEntity<Page<FavoriteDto>> find(Integer customerId ,FavoriteParam param, Optional<String> field, String known) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(0, 15, sort);
            Page<FavoriteDto> dto = this.repository.find(param, customerId, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(0, 15, sort);
            Page<FavoriteDto> dto = this.repository.find(param, customerId, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<FavoriteDto> dto = this.repository.find(param, customerId, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<FavoriteDto> create(FavoriteInput input) {
        /* Kiểm tra sản phẩm có tồn tại hay đang bị vô hiệu hóa hay ko */
        ProductEntity product = productRepository.findByIdAndStatusIsTrue(input.getProductId())
                .orElseThrow(()->new RuntimeException("Sản phẩm không khả dụng"));

        CustomerEntity customer = customerRepository.getById(input.getCustomerId());
        FavoriteEntity entity = new FavoriteEntity();
        entity.setProduct(product);
        entity.setCustomer(customer);

        int num = repository.countAllByProduct_IdAndCustomer_Id(input.getProductId(), input.getCustomerId());
        if(num >= 1){
            delete(input);
        }
        else{
            repository.save(entity);
        }
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<FavoriteDto> delete(FavoriteInput input) {
        FavoriteEntity entity = repository.findByCustomer_IdAndAndProduct_Id(input.getCustomerId(), input.getProductId());
        repository.delete(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    public int getProduct(Integer productId) {
        int count = repository.countAllByProduct_Id(productId);
        return count;
    }
}
