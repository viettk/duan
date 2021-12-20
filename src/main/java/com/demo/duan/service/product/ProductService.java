package com.demo.duan.service.product;

import com.demo.duan.service.category.param.CategoryParam;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.input.ProductCreateInput;
import com.demo.duan.service.product.input.ProductUpdateInput;
import com.demo.duan.service.product.param.ProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    ResponseEntity<Page<ProductDto>> searchByAdmin(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<List<ProductDto>> searchNewArrival();

    ResponseEntity<Page<ProductDto>> searchBySHF();

    ResponseEntity<Page<ProductDto>> searchByModelKit();

    ResponseEntity<Page<ProductDto>> searchByStaticModel();

    ResponseEntity<Page<ProductDto>> searchByKhac(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchAllByParent(CategoryParam cate, ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchAllModelKit( ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchAllStactic(ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchByCategoryName(Integer categoryId ,ProductParam param, Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<Page<ProductDto>> searchByCategoryParentName(String parentName , Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    ResponseEntity<ProductDto> getOne(Integer id);

    ResponseEntity<ProductDto> getOneAdmin(Integer id);


    ResponseEntity<Page<ProductDto>> findAllNotSearch(Optional<String> field, String known, Optional<Integer> limit, Optional<Integer> page);

    //dũng
    ResponseEntity<Integer> createProduct(ProductCreateInput input);

    ResponseEntity<ProductDto> insertImage(Integer id,String folder, Optional<MultipartFile> photo1, Optional<MultipartFile> photo2, Optional<MultipartFile> photo3, Optional<MultipartFile> photo4);

    ResponseEntity<ProductDto> updateImage(Integer id,String folder, Optional<MultipartFile> photo1, Optional<MultipartFile> photo2, Optional<MultipartFile> photo3, Optional<MultipartFile> photo4);

    ResponseEntity<ProductDto> updateProduct(ProductUpdateInput input);

    ResponseEntity<Page<ProductDto>> searchProduct(ProductParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);

//------------------------------------------------------------------------------------------
//    ResponseEntity<Page<ProductDto>> searchProduct(ProductParam param , Pageable page);

    ResponseEntity<List<ProductDto>> hideProduct(Integer[] ids);

    ResponseEntity<List<ProductDto>> presentProduct(Integer[] ids);

    ResponseEntity<List<ProductDto>> findAll(String name);


    //thống kê
    ResponseEntity<List<ProductDto>> Thongketop5spbanchay();

    ResponseEntity<List<ProductDto>> Thongketop5spbanchayTheoTime(Integer month, Integer year);

    List<Integer> soLuongBan5spBanChay(Integer month, Integer year);

    ResponseEntity<ProductDto> returnNumber(Integer id, Integer number);


    //Giảm giá
    public void valueDiscount(int value);

    public void khoiPhucGia();

    public void GiamGiaTheoDanhMuc(Integer categoryId, int value);

    public void GiamGiaTungSp(Integer id, int value);


    //get all product
    public ResponseEntity<Page<ProductDto>> getAllproduct(String param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);
    public ResponseEntity<Page<ProductDto>> getAllNotSearch(Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page);

    //gợi ý sản phẩm
    public ResponseEntity<List<ProductDto>> relatedProducts(Integer priceProduct);
}
