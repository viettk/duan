package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    @Query("select b from BillEntity b where b.email = :email and b.status_order is null or b.status_order =:status")
    Page<BillEntity>findByEmailAndOrder(@Param("email") String email, @Param("status") String status, Pageable pageable);

    @Query("select b from BillEntity b where b.status_pay is null or b.status_pay = :pay and b.status_order = :order")
    Page<BillEntity>findByStatus(@Param("pay") String pay, @Param("order") String order, Pageable pageable);

    Page<BillEntity>findByEmail(String email, Pageable pageable);

}
