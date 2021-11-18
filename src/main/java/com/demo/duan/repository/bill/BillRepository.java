package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    Page<BillEntity>findByEmail(String email, Pageable pageable);
    @Query("select b from BillEntity b where b.status_order = 'Giao hàng thành công'")
    Page<BillEntity>findByDone(Pageable pageable);
}
