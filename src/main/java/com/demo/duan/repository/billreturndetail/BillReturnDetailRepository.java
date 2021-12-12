package com.demo.duan.repository.billreturndetail;

import com.demo.duan.entity.BillReturnDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BillReturnDetailRepository extends JpaRepository<BillReturnDetailEntity, Integer> {
    List<BillReturnDetailEntity>findByBillReturn_Id(Integer id);

    @Query("select sum(d.total) from BillReturnDetailEntity d where d.billReturn.id=:id")
    BigDecimal totalBillRetrun(@Param("id") Integer id);
}
