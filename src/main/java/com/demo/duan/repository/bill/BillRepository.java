package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    Page<BillEntity>findByEmail(String email, Pageable pageable);
}
