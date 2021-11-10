package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.ThongkeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    @Query("select b from BillEntity b where b.status_order=:#{#status} ")
    List<BillEntity> all(String status);
    @Query("SELECT  ThongkeEntity(ct.product.id , sum(ct.number)) from  BillDetailEntity  ct  , BillEntity hd where ct.bill.id= hd.id AND hd.update_date BETWEEN :startDate AND :endDate GROUP BY ct.product.id")
    List<ThongkeEntity> Thongke(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

//    @Query("SELECT new ThongkeEntity(o.product.id ,sum(o.number))  FROM BillDetailEntity o where BillEntity.update_date BETWEEN :startDate AND :endDate GROUP BY o.product")
//    List<ThongkeEntity> Thongke(@Param("startDate")Date startDate, @Param("endDate")Date endDate);


}
