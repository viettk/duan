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

    @Query(nativeQuery = true , value = "from Discount d where DATEDIFF(year, end_day, GETDATE()) = 0" +
            "and DATEDIFF(month, end_day, GETDATE()) = 0 and DATEDIFF(day, end_day, GETDATE()) > 0 and number > 0")
    DiscountEntity getDiscountName(String name);

    @Query(nativeQuery = true , value = "select * from Discount d where DATEDIFF(year, end_day, GETDATE()) = 0" +
            "and DATEDIFF(month, end_day, GETDATE()) = 0 and DATEDIFF(day, end_day, GETDATE()) < 0 and number > 0 and name= :name" +
            "and DATEDIFF(year, open_day, GETDATE()) = 0 and DATEDIFF(month, open_day, GETDATE()) = 0 and DATEDIFF(day, open_day, GETDATE()) > 0")
    Optional<DiscountEntity> searchdiscount(@Param("name") String name);

    //postgresql
//    @Query(nativeQuery = true , value = "from Discount d where DATE_PART('year', end_day::date ) - DATE_PART('year', start) = 0" +
//            "and DATEDIFF(month, end_day, GETDATE()) = 0 and DATEDIFF(day, end_day, GETDATE()) > 0 and number > 0")
//    DiscountEntity getDiscountNamePost(String name);

    long countAllByName(String name);

    @Query("from DiscountEntity d where (:#{#param.name} is null or d.name like %:#{#param.name}%)" +
            "and (:#{#param.valueDiscount} is null or d.valueDiscount >= :#{#param.valueDiscount})")
    Page<DiscountEntity> find(@Param("param")DiscountParam param, Pageable pageable);
}
