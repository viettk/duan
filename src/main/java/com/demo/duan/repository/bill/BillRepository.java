package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.ProductEntity;
import com.demo.duan.service.bill.param.BillParam;
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

    @Query("from BillEntity b where b.email = :email and (:#{#bill.status_order} is null or b.status_order = :#{#bill.status_order})")
    Page<BillEntity> getBillCustomer (@Param("email") String email, @Param("bill") BillParam bill , Pageable pageable);

//    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE date_part('month', update_date) = :month and date_part('year', update_date)= :year and status_order = N'Đã hủy'" , nativeQuery = true)
//    Integer donhuy(@Param("month") Integer month, @Param("year") Integer year);

//    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE date_part('month', update_date) = :month and date_part('year', update_date)= :year and status_order = N'Đã thanh toán'" , nativeQuery = true)
//    Integer dontc(@Param("month") Integer month, @Param("year") Integer year);
//
//    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  date_part('month', update_date) = :month and date_part('year', update_date)= :year and status_order = N'Đã hoàn trả'" , nativeQuery = true)
//    Integer dontra(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = 5" , nativeQuery = true)
    Integer donhuy(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = 4" , nativeQuery = true)
    Integer dontc(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = 2" , nativeQuery = true)
    Integer dontra(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.type_pay = false ")
    Integer thongketype_payCOD();

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.type_pay = true")
    Integer thongketype_payVNPAY();

//    @Query ( value="SELECT sum(hd.total) FROM Bill hd WHERE date_part('month', create_date) = :month and date_part('year', update_date) = :year" , nativeQuery = true)
//    BigDecimal thongkedoanhthu(@Param("month")Integer month, Integer year);

    @Query ( value="SELECT sum(hd.total) FROM Bill hd WHERE MONTH(hd.create_date) = :month and year(hd.create_date) = :year" , nativeQuery = true)
    BigDecimal thongkedoanhthu(@Param("month")Integer month, Integer year);

    @Query("select b from BillEntity b where :#{#bill.status_order} is null or b.status_order=:#{#bill.status_order} " +
            "and (:#{#bill.status_pay} is null or b.status_pay=:#{#bill.status_pay})" +
            "and :#{#bill.date_start} is null and :#{#bill.date_end} is null or b.create_date between :#{#bill.date_start} and :#{#bill.date_end}")
    Page<BillEntity> filterBill(@Param("bill") BillParam param, Pageable pageable);

    @Query("select b from BillEntity b where b.email=:email " +
            "and (:#{#bill.status_order} is null or b.status_order=:#{#bill.status_order})" +
            "and (:#{#bill.status_pay} is null or b.status_pay=:#{#bill.status_pay}) " +
            "and (:#{#bill.date_start} is null and :#{#bill.date_end} is null) " +
            "or(b.create_date between :#{#bill.date_start} and :#{#bill.date_end}) ")
    Page<BillEntity> findByEmail(@Param("email") String email, @Param("bill") BillParam param, Pageable pageable);

}
