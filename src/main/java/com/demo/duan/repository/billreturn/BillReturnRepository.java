package com.demo.duan.repository.billreturn;

import com.demo.duan.entity.BillReturnEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillReturnRepository extends JpaRepository<BillReturnEntity, Integer> {
    Page<BillReturnEntity>findByStatus(String status, Pageable pageable);
}
