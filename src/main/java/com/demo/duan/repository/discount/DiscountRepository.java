package com.demo.duan.repository.discount;

import com.demo.duan.entity.DiscountEntity;
import com.demo.duan.service.discount.param.DiscountParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Integer> {
    @Query("from DiscountEntity d where d.name=:name and d.number > 0")
    Optional<DiscountEntity> searchDiscountByCustomer(@Param("name") String name);

    DiscountEntity getByName(String name);

    long countAllByName(String name);

    @Query("from DiscountEntity d where (:#{#param.name} is null or d.name like %:#{#param.name}%)" +
            "and (:#{#param.valueDiscount} is null or d.valueDiscount >= :#{#param.valueDiscount})")
    Page<DiscountEntity> find(@Param("param")DiscountParam param, Pageable pageable);
}
