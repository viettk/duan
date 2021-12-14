package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.service.bill.param.BillParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {

    @Query("select b from BillEntity b where :#{#bill.status_order} is null or b.status_order like %:#{#bill.status_order}% " +
            "and (:#{#bill.status_pay} is null or b.status_pay like %:#{#bill.status_pay}%) " +
            "and :#{#bill.date_start} is null and :#{#bill.date_end} is null or b.create_date between :#{#bill.date_start} and :#{#bill.date_end}")
    Page<BillEntity> filterBill(@Param("bill") BillParam param, Pageable pageable);

    @Query("select b from BillEntity b where b.email=:email " +
            "and (:#{#bill.status_order} is null or b.status_order like %:#{#bill.status_order}%) " +
            "and (:#{#bill.status_pay} is null or b.status_pay like %:#{#bill.status_pay}%) " +
            "and (:#{#bill.date_start} is null and :#{#bill.date_end} is null) " +
            "or(b.create_date between :#{#bill.date_start} and :#{#bill.date_end}) ")
    Page<BillEntity> findByEmail(@Param("email") String email, @Param("bill") BillParam param, Pageable pageable);

}
