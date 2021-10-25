package com.demo.duan.repository.billdetail;

import com.demo.duan.entity.BillDetailEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetailEntity, Integer> {
    List<BillDetailEntity>findByBill_Id(Integer id, Sort sort);
}
