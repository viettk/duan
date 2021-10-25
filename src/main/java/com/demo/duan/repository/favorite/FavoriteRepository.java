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

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    @Query("from FavoriteEntity f where f.product.status=false " +
            "and (:#{#product.name} is null or f.product.name like %:#{#product.name}%)" +
            "and (:#{#product.price} is null or f.product.price >= :#{#product.price})" +
            "and f.customer.id = :customerId")
    Page<FavoriteEntity> find(@Param("product")FavoriteParam product, Integer customerId,Pageable pageable);

    FavoriteEntity findByCustomer_IdAndAndProduct_Id(Integer customerId, Integer productId);

    int countAllByProduct_Id(Integer productId);

}
