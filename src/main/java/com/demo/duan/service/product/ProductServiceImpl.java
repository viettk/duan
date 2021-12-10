package com.demo.duan.service.product;

import com.demo.duan.entity.CategoryEntity;
import com.demo.duan.entity.PhotoEntity;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.repository.category.CategoryRepository;
import com.demo.duan.repository.photo.PhotoRepository;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.category.param.CategoryParam;
import com.demo.duan.service.customer.param.CustomerMapper;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.input.ProductCreateInput;
import com.demo.duan.service.product.input.ProductUpdateInput;
import com.demo.duan.service.product.mapper.ProductMapper;
import com.demo.duan.service.product.mapper.ProductUpdateMapper;
import com.demo.duan.service.product.param.ProductParam;
import com.demo.duan.service.upload.UpLoadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    private final ProductUpdateMapper updateMapper;

    private final CategoryRepository categoryRepository;

    private final UpLoadService upLoadService;

    private final PhotoRepository photoRepository;
    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchByAdmin(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByAdmin(param, pageable).map(mapper::entityToDto);

            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByAdmin(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ProductDto> dto = this.productRepository.searchByAdmin(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<ProductDto>> searchNewArrival() {
        List<ProductEntity> entities = this.productRepository.searchNewArrival();
        List<ProductDto> dto = mapper.EntitiesToDtos(entities);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchBySHF() {
        Pageable pageable = PageRequest.of(0,21);
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
    public ResponseEntity<Page<ProductDto>> searchByKhac( ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllKhac(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllKhac(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ProductDto> dto = this.productRepository.searchAllKhac(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchAllByParent(CategoryParam cate, ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllSHF(cate, param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllSHF(cate, param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ProductDto> dto = this.productRepository.searchAllSHF(cate,param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchAllModelKit(ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllModelKit(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllModelKit(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ProductDto> dto = this.productRepository.searchAllModelKit(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchAllStactic( ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllStaticModel(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAllStaticModel(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ProductDto> dto = this.productRepository.searchAllStaticModel(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchByCategoryName(Integer categoryId,ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByCategoryName(categoryId, param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByCategoryName(categoryId, param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ProductDto> dto = this.productRepository.searchByCategoryName(categoryId, param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    public ResponseEntity<Page<ProductDto>> searchByCategoryParentName(String parentName, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByCategoryParentName(parentName, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else if(!known.equals("up") || known.equals("")){
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByCategoryParentName(parentName, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Pageable pageable = PageRequest.of(0, 15);
            Page<ProductDto> dto = this.productRepository.searchByCategoryParentName(parentName, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ProductDto> getOne(Integer id) {
        /* Kiểm tra id của sản phẩm có tồn tại hay ko */
        ProductEntity entity = this.productRepository.findByIdAndStatusIsTrue(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
       return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<ProductDto> getOneAdmin(Integer id) {
        ProductEntity entity = this.productRepository.getById(id);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    //    -------------------- Dũng  quản lý sản phẩm -------------------------------
    @Override
    @Transactional
    public ResponseEntity<Integer> createProduct(ProductCreateInput input) {
        ProductEntity product = mapper.inputToEntity(input);
        CategoryEntity category = categoryRepository.getById(input.getCategoryID());
        product.setCategory(category);
        product.setValue_extra(0);
        product.setPrice(product.getPrice_extra().multiply(BigDecimal.valueOf(100-product.getValue_extra())).divide(BigDecimal.valueOf(100)));
        //thêm sản phẩm
        ProductEntity productSave = productRepository.save(product);
        return ResponseEntity.ok(productSave.getId());
    }

    @Override
    @Transactional
    public ResponseEntity<ProductDto> insertImage(Integer id,String folder, Optional<MultipartFile> photo1, Optional<MultipartFile> photo2, Optional<MultipartFile> photo3, Optional<MultipartFile> photo4) {
        ProductEntity product = productRepository.getById(id);
        if(!photo1.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo1.get(), folder);
            product.setPhoto(filePhoto.getName());
        }
        if(!photo2.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo2.get(), folder);
            photoRepository.save(new PhotoEntity(null,filePhoto.getName(),product));
        }
        if(!photo3.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo3.get(), folder);
            photoRepository.save(new PhotoEntity(null,filePhoto.getName(),product));
        }
        if(!photo4.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo4.get(), folder);
            photoRepository.save(new PhotoEntity(null,filePhoto.getName(),product));
        }
        return ResponseEntity.ok(mapper.entityToDto(productRepository.save(product)));
    }

    @Override
    @Transactional
    public ResponseEntity<ProductDto> updateProduct(ProductUpdateInput input) {
        ProductEntity product = productRepository.getById(input.getId());
        updateMapper.inputToEntity(input,product);
        if(product.getValue_extra()==null){
            product.setValue_extra(0);
        }
        product.setPrice(product.getPrice_extra().multiply(BigDecimal.valueOf(100-product.getValue_extra())).divide(BigDecimal.valueOf(100)));
        CategoryEntity category = categoryRepository.getById(input.getCategoryID());
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok(mapper.entityToDto(product));
    }

    @Override
    @Transactional
    public ResponseEntity<ProductDto> updateImage(Integer id,String folder, Optional<MultipartFile> photo1, Optional<MultipartFile> photo2, Optional<MultipartFile> photo3, Optional<MultipartFile> photo4) {
        ProductEntity product = productRepository.getById(id);
        List<PhotoEntity> photos = photoRepository.findAllByProduct_Id(id);
        Integer length = photos.size();
        if(!photo1.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo1.get(), folder);
            product.setPhoto(filePhoto.getName());
        }
        PhotoEntity photo = null;
        if(!photo2.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo2.get(), folder);
            if(length >0) {
                photo = photos.get(0);
                photo.setName(filePhoto.getName());
            }else{
                photo = new PhotoEntity(null, filePhoto.getName(), product);
            }
            photoRepository.save(photo);
        }
        if(!photo3.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo3.get(), folder);
            if(length >1) {
                photo = photos.get(1);
                photo.setName(filePhoto.getName());
            }else{
                photo = new PhotoEntity(null, filePhoto.getName(), product);
            }
            photoRepository.save(photo);
        }
        if(!photo4.isEmpty()) {
            File filePhoto = upLoadService.savePhoto(photo4.get(), folder);
            if(length >2) {
                photo = photos.get(2);
                photo.setName(filePhoto.getName());
            }else{
                photo = new PhotoEntity(null, filePhoto.getName(), product);
            }
            photoRepository.save(photo);
        }
        return ResponseEntity.ok(mapper.entityToDto(productRepository.save(product)));
    }

    // Tìm kiếm sản phẩm
    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> searchProduct(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.get().equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByAdmin(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchByAdmin(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }
    // ẩn sản phẩm
    @Override
    @Transactional
    public ResponseEntity<List<ProductDto>> hideProduct(Integer[] ids) {
        List<ProductEntity> listProduct = productRepository.findByIdIn(ids);
        listProduct.forEach(p->{
            if(p.isStatus()==false){
                p.setStatus(true);
            }else {
                p.setStatus(false);
            }
            productRepository.save(p);
        });
        List<ProductDto> listProductDtos = listProduct.stream().map(mapper::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(listProductDtos);
    }

//    -------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public ResponseEntity<List<ProductDto>> presentProduct(Integer[] ids) {
        List<ProductEntity> listProduct = productRepository.findByIdInAndStatusIsTrue(ids);
        listProduct.forEach(p->{
            p.setStatus(false);
            productRepository.save(p);
        });
        List<ProductDto> listProductDtos = listProduct.stream().map(mapper::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(listProductDtos);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ProductDto>> findAll(String name) {
        List<ProductEntity> lstenEntities = productRepository.search(name);
        List<ProductDto> lstProductDtos = mapper.EntitiesToDtos(lstenEntities);
        return ResponseEntity.ok().body(lstProductDtos);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ProductDto>> Thongketop5spbanchay() {
        List<ProductEntity> lst = productRepository.Thongketop5spbanchay();
        List<ProductDto> dtos = mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(dtos);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ProductDto>> Thongketop5spbanchayTheoTime(Integer month, Integer year) {
        List<ProductEntity> entityList = productRepository.Thongketop5spbanchayTheoThangNam(month, year);
        List<ProductDto> productDtos = mapper.EntitiesToDtos(entityList);
        return ResponseEntity.ok().body(productDtos);
    }

    @Override
    public List<Integer> soLuongBan5spBanChay(Integer month, Integer year) {
        List<Integer> integers = productRepository.soLuongBan5spBanChay(month, year);
        return integers;
    }

    @Override
    public ResponseEntity<ProductDto> returnNumber(Integer id, Integer number) throws RuntimeException{
        ProductEntity entity = this.productRepository.findById(id).orElseThrow( () -> new RuntimeException("Sản phẩm này không tồn tại"));
        Integer a = entity.getNumber();
        entity.setNumber(a + number);
        this.productRepository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public void valueDiscount(int value) {
        BigDecimal hunderd = new BigDecimal(100);
        List<ProductEntity> lst = productRepository.findAll();
        for(ProductEntity x: lst){
            x.setPrice(x.getPrice_extra());
            x.setValue_extra(value);
            x.setPrice( x.getPrice().subtract(x.getPrice().multiply(BigDecimal.valueOf(value)).divide(hunderd)) );
        }
    }

    @Override
    @Transactional
    public void khoiPhucGia() {
        List<ProductEntity> lst = productRepository.findAll();
        for(ProductEntity x: lst){
            x.setPrice(x.getPrice_extra());
            x.setValue_extra(0);
        }
    }

    @Override
    @Transactional
    public void GiamGiaTheoDanhMuc(Integer categoryId, int value) {
        BigDecimal hunderd = new BigDecimal(100);
        List<ProductEntity> lst = productRepository.findAllByCategory_Id(categoryId);
        for(ProductEntity x: lst){
            x.setPrice(x.getPrice_extra());
            x.setPrice( x.getPrice().subtract(x.getPrice().multiply(BigDecimal.valueOf(value)).divide(hunderd)) );
            x.setValue_extra(value);
        }
    }

    @Override
    @Transactional
    public void GiamGiaTungSp(Integer id, int value) {
        BigDecimal hunderd = new BigDecimal(100);
        ProductEntity entity = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Mã Sản phẩm không tồn tại"));
        entity.setPrice(entity.getPrice_extra());
        entity.setPrice(entity.getPrice().subtract(entity.getPrice().multiply(BigDecimal.valueOf(value)).divide(hunderd)));
        productRepository.save(entity);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<ProductDto>> getAllproduct(String param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page) {
        if(known.get().equals("up")){
            Sort sort =Sort.by(Sort.Direction.ASC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAll(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            Sort sort =Sort.by(Sort.Direction.DESC, field.orElse("id"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), sort);
            Page<ProductDto> dto = this.productRepository.searchAll(param, pageable).map(mapper::entityToDto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<ProductDto>> relatedProducts(Integer priceProduct) {
        Pageable pageable = PageRequest.of(0,5) ;
        List<ProductEntity> entities = productRepository.relatedProducts(priceProduct, pageable);
        List<ProductDto> productDtos = mapper.EntitiesToDtos(entities);
        return ResponseEntity.ok().body(productDtos);
    }

}
