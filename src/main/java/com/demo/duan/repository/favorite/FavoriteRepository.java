package com.demo.duan.repository.favorite;

import com.demo.duan.entity.FavoriteEntity;
import com.demo.duan.service.favorite.input.FavoriteInput;
import com.demo.duan.service.favorite.param.FavoriteParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    @Query("from FavoriteEntity f where f.product.status=true " +
            "and (:#{#product.name} is null or f.product.name like %:#{#product.name}%)" +
            "and (:#{#product.price} is null or f.product.price >= :#{#product.price})" +
            "and f.customer.id = :customerId")
    Page<FavoriteEntity> find(@Param("product")FavoriteParam product, Integer customerId,Pageable pageable);

    @Query("from FavoriteEntity f where f.customer.id = :customerId and f.product.id = :productId")
    FavoriteEntity finfFavo(Integer customerId, Integer productId);

    @Query("select count(f.id) from FavoriteEntity f where f.customer.id = :customerId and f.product.id = :productId " +
            "and f.customer.email = :email")
    int count(@Param("customerId") Integer customerId, @Param("productId") Integer productId,@Param("email") String email);

    int countAllByProduct_Id(Integer productId);

    int countAllByProduct_IdAndCustomer_Id(Integer productId, Integer customerId);

    FavoriteEntity findByCustomer_IdAndProduct_Id(Integer customer_id, Integer product_id);

}
