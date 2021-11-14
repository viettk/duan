package com.demo.duan.repository.receiptdetail;

import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.entity.ReceiptDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetailEntity, Integer > {
        @Query("select r from ReceiptDetailEntity r")
        Page<ReceiptDetailEntity> all(Pageable pageable);
        @Query("select r from ReceiptDetailEntity r where r.receipt=:id")
        Optional<ReceiptDetailEntity>findByIdRecript(Integer id);
    }
