package com.demo.duan.repository.billdetail;

import com.demo.duan.entity.BillDetailEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetailEntity, Integer> {

    @Query("select b from BillDetailEntity b where b.bill.id = :id")
    List<BillDetailEntity>findByBill(@Param("id") Integer id);

    @Query("select sum(b.total) from BillDetailEntity b where b.bill.id = :billId")
    BigDecimal totalOfBill(@Param("billId") Integer billId );

    @Query("select b from BillDetailEntity b where b.bill.id = :id")
    List<BillDetailEntity> getListByCustomer(@Param("id") Integer id);

    /*bill admin*/
    @Query("select b from BillDetailEntity b where b.bill.id = :id")
    List<BillDetailEntity>findByBill(@Param("id") Integer id, Sort sort);

    @Query("select sum(cd.product.weight) from BillDetailEntity cd where cd.bill.id = :billId")
    Float tinhTongCanNangCart(@Param("billId") Integer billId);

    @Query("select sum(cd.total) from BillDetailEntity cd where cd.bill.id = :billId")
    BigDecimal tinhTongPrice(@Param("billId") Integer billId);
}
