package com.demo.duan.service.product;

import com.demo.duan.entity.CategoryEntity;
import com.demo.duan.entity.PhotoEntity;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.repository.category.CategoryRepository;
import com.demo.duan.repository.photo.PhotoRepository;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.photo.PhotoService;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.input.ProductCreateInput;
import com.demo.duan.service.product.mapper.ProductMapper;
import com.demo.duan.service.product.param.ProductParam;
import com.demo.duan.service.upload.UpLoadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    private final CategoryRepository categoryRepository;

    private final UpLoadService upLoadService;

    private final PhotoRepository photoRepository;


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

    @Override
    @Transactional
    public ResponseEntity<ProductDto> createProduct(String folder,ProductCreateInput input) {
        ProductEntity product = mapper.inputToEntity(input);
        CategoryEntity category = categoryRepository.getById(input.getCategoryID());
        product.setCategory(category);
        //thêm sản phẩm
        ProductEntity productSave = productRepository.save(product);
        //thêm ảnh vào sản phẩm
        File filePhoto1 = upLoadService.savePhoto(input.getPhoto1(), folder);
        PhotoEntity photo1 = new PhotoEntity(null,filePhoto1.getName(),productSave);
        photoRepository.save(photo1);
        if(!input.getPhoto2().isEmpty()){
            File filePhoto2 = upLoadService.savePhoto(input.getPhoto2(), folder);
            PhotoEntity photo2 = new PhotoEntity(null,filePhoto2.getName(),productSave);
            photoRepository.save(photo2);
        }
        if(!input.getPhoto3().isEmpty()){
            File filePhoto3 = upLoadService.savePhoto(input.getPhoto3(), folder);
            PhotoEntity photo3 = new PhotoEntity(null,filePhoto3.getName(),productSave);
            photoRepository.save(photo3);
        }
        if(!input.getPhoto4().isEmpty()){
            File filePhoto4 = upLoadService.savePhoto(input.getPhoto4(), folder);
            PhotoEntity photo4 = new PhotoEntity(null,filePhoto4.getName(),productSave);
            photoRepository.save(photo4);
        }

        //tìm sản phẩm vừa thêm
        ProductEntity productEntity = productRepository.getById(productSave.getId());
        return ResponseEntity.ok(mapper.entityToDto(productEntity));
    }
}
