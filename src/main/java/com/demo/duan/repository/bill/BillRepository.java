package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    @Query("select b from BillEntity b where b.status_order=:#{#status} ")
    List<BillEntity> all(String status);
}
