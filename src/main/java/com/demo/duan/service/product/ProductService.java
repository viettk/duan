package com.demo.duan.service.product;

import com.demo.duan.service.category.param.CategoryParam;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {

    ResponseEntity<Page<ProductDto>> searchByAdmin(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchNewArrival();

    ResponseEntity<Page<ProductDto>> searchBySHF();

    ResponseEntity<Page<ProductDto>> searchByModelKit();

    ResponseEntity<Page<ProductDto>> searchByStaticModel();

    ResponseEntity<Page<ProductDto>> searchByKhac(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchAllByParent(CategoryParam cate, ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchAllModelKit( ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchAllStactic(ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchByCategoryName(Integer categoryId ,ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<ProductDto> getOne(Integer id);

    ResponseEntity<ProductDto> getOneAdmin(Integer id);
}
