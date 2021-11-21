package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    long countAllByEmail(String email);

    @Query("from BillEntity b where b.email = :email ")
    Page<BillEntity> getBillCustomer (@Param("email") String email, Pageable pageable);

    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = N'Đã hủy'" , nativeQuery = true)
    Integer donhuy(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = N'Đã thanh toán'" , nativeQuery = true)
    Integer dontc(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = N'Đã hoàn trả'" , nativeQuery = true)
    Integer dontra(@Param("month") Integer month, @Param("year") Integer year);

    @Query ( value="SELECT sum(hd.total) FROM Bill hd WHERE MONTH(hd.create_date) = :month and year(hd.create_date) = :year" , nativeQuery = true)
    BigDecimal thongkedoanhthu(@Param("month")Integer month, Integer year);

    @Query("select b from BillEntity b where b.status_pay = :pay and b.status_order = :order")
    Page<BillEntity>findByStatus(@Param("pay") String pay, @Param("order") String order, Pageable pageable);

    Page<BillEntity>findByEmail(String email, Pageable pageable);

}
