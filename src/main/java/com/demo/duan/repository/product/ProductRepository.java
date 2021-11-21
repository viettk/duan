package com.demo.duan.repository.product;

import com.demo.duan.entity.ProductEntity;
import com.demo.duan.service.category.param.CategoryParam;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(nativeQuery = true,
            value = "select top 20 p.* " +
                    "FROM Product  p " +
                    "GROUP BY  p.id ,p.name,p.category_id,p.status,p.number,p.price,p.describe,p.photo,p.create_date,p.sku,p.trait,p.release_date,p.price_release,p.weight,p.height,p.width,p.length " +
                    "ORDER BY p.id desc ")
    List<ProductEntity> searchNewArrival();


    /* select theo danh mục cha: SHF */
    @Query("from ProductEntity p where p.status = true and p.category.parent_name like 'SHF'")
    Page<ProductEntity> searchBySHF(Pageable pageable );

    /* select theo danh mục cha: Mô hình tĩnh */
    @Query("from ProductEntity p where p.status = true and p.category.parent_name like 'Static Model' ")
    Page<ProductEntity> searchByStaticModel(Pageable pageable );

    @Query("from ProductEntity p where p.status = true and p.category.parent_name like 'Model Kit'")
    Page<ProductEntity> searchByModelKit(Pageable pageable );



    /* xem, lọc sản phẩm theo từng danh mục con */
    @Query("from ProductEntity p where p.status = true and p.category.id = :categoryId " +
            "and (:#{#product.name} is null or p.name like %:#{#product.name}%)" +
            "and (:#{#product.categoryName} is null or p.category.id >= :#{#product.categoryName})" +
            "and (:#{#product.price} is null or p.price >= :#{#product.price})")
    Page<ProductEntity> searchByCategoryName(@Param("categoryId") Integer categoryId ,@Param("product") ProductParam product, Pageable pageable );


    @Query("from ProductEntity p where p.status = true and (:#{#cate.name} is null or p.category.name like %:#{#cate.name}%)" +
            "and (:#{#cate.parent_name} is null or p.category.parent_name like %:#{#cate.parent_name}%)" +
            "and (:#{#product.name} is null or p.name like %:#{#product.name}%)" +
            "and (:#{#product.price} is null or p.price >= :#{#product.price})")
    Page<ProductEntity> searchAllSHF(@Param("cate")CategoryParam cate, @Param("product") ProductParam product, Pageable pageable );

    @Query("from ProductEntity p where p.status = true and p.category.name like 'Khác' " +
            "and (:#{#product.name} is null or p.name like %:#{#product.name}%)" +
            "and (:#{#product.price} is null or p.price = :#{#product.price})")
    Page<ProductEntity> searchAllKhac(@Param("product") ProductParam product, Pageable pageable );

    @Query("from ProductEntity p where p.status = true and p.category.name like 'StaticModel' " +
            "and (:#{#product.name} is null or p.name like :#{#product.name})" +
            "and (:#{#product.price} is null or p.price >= :#{#product.price})")
    Page<ProductEntity> searchAllStaticModel(@Param("product") ProductParam product, Pageable pageable );

    @Query("from ProductEntity p where p.status = true and p.category.name like 'Model Kit' " +
            "and (:#{#product.name} is null or p.name like %:#{#product.name}%)" +
            "and (:#{#product.price} is null or p.price = :#{#product.price})")
    Page<ProductEntity> searchAllModelKit(@Param("product") ProductParam product, Pageable pageable );

    Optional<ProductEntity> findByIdAndStatusIsFalse(Integer id);

    /* tìm sản phẩm liên quan theo giá */
    @Query("from ProductEntity p where p.status =true and" +
            " p.category.id = :categoryId order by p.id ASC")
    List<ProductEntity> relatedProducts(Integer categoryId);

    Optional<ProductEntity> findByIdAndStatusIsTrue(Integer id);

    List<ProductEntity> findTop5ByCategory_Id(Integer categoryId);

    @Query("from ProductEntity p where (:#{#product.name} is null or p.name like %:#{#product.name}%)" +
            "and (:#{#product.categoryName} is null or p.category.name = :#{#product.categoryName})")
    Page<ProductEntity> searchByAdmin(@Param("product") ProductParam product, Pageable pageable );

    // ---------------------------dũng--------------------
    @Query("select p from ProductEntity p where " +
            "(:#{#product.name} is null or p.name like %:#{#product.name}%)" +
            "and (:#{#product.id} is null or p.id = :#{#product.id})" +
            "and (:#{#product.categoryId} is null or p.category.id = :#{#product.categoryId})" +
            "and (:#{#product.price} is null or p.price >= :#{#product.price})")
    Page<ProductEntity> searchProduct(@Param("product") ProductParam product, Pageable pageable);


    List<ProductEntity> findByIdInAndStatusIsFalse(Integer[] ids);

    List<ProductEntity> findByIdInAndStatusIsTrue(Integer[] ids);


    /* Cho phiếu nhập chi tiết */
    @Query("from ProductEntity p where :name is null or p.name like %:name%")
    List<ProductEntity> search(String name);

    @Query(nativeQuery = true,
            value = "SELECT TOP 5 p.* " +
                    "FROM Product  p join Bill_detail bd on p.id = bd.product_id " +
                    "GROUP BY  p.id ,p.name,p.category_id,p.status,p.number,p.price,p.describe,p.photo,p.create_date,p.sku,p.trait,p.release_date,p.price_release,p.weight,p.height,p.width,p.length " +
                    "ORDER BY SUM(bd.number) desc")
    List<ProductEntity> Thongketop5spbanchay();

    @Query(nativeQuery = true,
            value = "SELECT TOP 5 p.* FROM Product  p join Bill_detail bd on p.id = bd.product_id join Bill b on b.id = bd.bill_id where MONTH(b.create_date) = :month and YEAR(b.create_date) = :year " +
                    "GROUP BY  p.id ,p.name,p.category_id,p.status,p.number,p.price,p.describe,p.photo,p.create_date,p.sku,p.trait,p.release_date,p.price_release,p.weight,p.height,p.width,p.length " +
                    "ORDER BY SUM(bd.number) desc ")
    List<ProductEntity> Thongketop5spbanchayTheoThangNam(Integer month, Integer year);

    @Query(nativeQuery = true,
            value = "SELECT TOP 5 sum(bd.number) FROM Product  p join Bill_detail bd on p.id = bd.product_id join Bill b on b.id = bd.bill_id where MONTH(b.create_date) = :month and YEAR(b.create_date) = :year " +
                    "GROUP BY  p.id, p.photo, p.name\n" +
                    "ORDER BY SUM(bd.number) desc ")
    List<Integer> soLuongBan5spBanChay(Integer month, Integer year);
}
