package com.demo.duan.repository.discount;

import com.demo.duan.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Integer> {
    @Query("from DiscountEntity d where d.name=:name and d.number > 0")
    Optional<DiscountEntity> searchDiscountByCustomer(@Param("name") String name);

    long countAllByName(String name);
}
