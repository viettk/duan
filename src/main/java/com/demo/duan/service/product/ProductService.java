package com.demo.duan.service.product;

import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.input.ProductCreateInput;
import com.demo.duan.service.product.input.ProductUpdateInput;
import com.demo.duan.service.product.param.ProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    ResponseEntity<Integer> createProduct(ProductCreateInput input);

    ResponseEntity<ProductDto> insertImage(Integer id,String folder, Optional<MultipartFile> photo1, Optional<MultipartFile> photo2, Optional<MultipartFile> photo3, Optional<MultipartFile> photo4);

    ResponseEntity<ProductDto> updateProduct(String folder, Integer id, ProductUpdateInput input, Optional<MultipartFile> photo1, Optional<MultipartFile> photo2, Optional<MultipartFile> photo3, Optional<MultipartFile> photo4);

    ResponseEntity<Page<ProductDto>> searchProduct(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<List<ProductDto>> hideProduct(Integer[] ids);

    ResponseEntity<List<ProductDto>> presentProduct(Integer[] ids);

    ResponseEntity<ProductDto> hiddenOneProduct(Integer id);
}
