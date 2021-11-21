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
    Page<BillEntity>findByEmail(String email, Pageable pageable);

    @Query("select b from BillEntity b where b.status_pay = :pay and b.status_order = :order")
    Page<BillEntity>findByStatus(@Param("pay") String pay, @Param("order") String order, Pageable pageable);
}
