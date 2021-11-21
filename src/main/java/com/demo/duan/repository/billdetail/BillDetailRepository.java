package com.demo.duan.repository.billdetail;

import com.demo.duan.entity.BillDetailEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetailEntity, Integer> {
    @Query("select b from BillDetailEntity b where b.bill.id = :id")
    List<BillDetailEntity>findByBill(@Param("id") Integer id, Sort sort);
}
