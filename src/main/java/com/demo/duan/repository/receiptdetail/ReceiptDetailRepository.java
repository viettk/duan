package com.demo.duan.repository.receiptdetail;

import com.demo.duan.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptDetailRepository extends JpaRepository<ReceiptEntity, Integer> {
}
