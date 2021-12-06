package com.demo.duan.service.checkcartlocal;

import com.demo.duan.entity.LocalStorageBillDetail;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CheckCartServiceImpl implements CheckCartService{

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ResponseEntity<List<LocalStorageBillDetail>> checkCart(List<LocalStorageBillDetail> inputs) {
        int num = 0;
        for(LocalStorageBillDetail x : inputs) {
            ProductEntity productEntity = productRepository.getById(x.getProduct_id());
            num = num + x.getNumber();
            if(productEntity.getNumber() < x.getNumber() ){
                throw new RuntimeException("Sản phẩm " + productEntity.getName() + " không đủ để mua");
            }

        }
        if(num >=15){
            throw new RuntimeException("Bạn không thể mua quá 15 sản phẩm trong 1 đơn hàng");
        }
        return ResponseEntity.ok().body(inputs);
    }
}
