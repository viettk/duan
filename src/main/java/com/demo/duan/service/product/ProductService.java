package com.demo.duan.service.product;

import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {
    ResponseEntity<Page<ProductDto>> searchNewArrival();

    ResponseEntity<Page<ProductDto>> searchBySHF();

    ResponseEntity<Page<ProductDto>> searchByModelKit();

    ResponseEntity<Page<ProductDto>> searchByStaticModel();

    ResponseEntity<Page<ProductDto>> searchByKhac(ProductParam param, Optional<String> field, String known);

    ResponseEntity<Page<ProductDto>> searchAllSHF(ProductParam param, Optional<String> field, String known);

    ResponseEntity<Page<ProductDto>> searchAllModelKit( ProductParam param, Optional<String> field, String known);

    ResponseEntity<Page<ProductDto>> searchAllStactic(ProductParam param, Optional<String> field, String known);

    ResponseEntity<Page<ProductDto>> searchByCategoryName(Integer categoryId ,ProductParam param, Optional<String> field, String known);

    ResponseEntity<ProductDto> getOne(Integer id);
}
