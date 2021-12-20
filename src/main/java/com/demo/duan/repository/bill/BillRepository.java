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
import java.time.LocalDate;
import java.util.Date;
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

    //đơn thất bại
    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = 4" , nativeQuery = true)
    Integer donhuy(@Param("month") Integer month, @Param("year") Integer year);

    //đơn thành công
    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = 3" , nativeQuery = true)
    Integer dontc(@Param("month") Integer month, @Param("year") Integer year);

    //Đơn từ chối
    @Query(value="sELECT count(Bill.id) as sodonhuy FROM  Bill WHERE  MONTH(update_date) = :month and YEAR(update_date)= :year and status_order = 5" , nativeQuery = true)
    Integer dontra(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(b.id) as sodonhuy FROM  Bill b WHERE b.type_pay = false and MONTH(b.update_date) = :month and YEAR(b.update_date)= :year", nativeQuery = true)
    Integer thongketype_payCOD(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(b.id) as sodonhuy FROM  Bill b WHERE b.type_pay = true and MONTH(b.update_date) = :month and YEAR(b.update_date)= :year", nativeQuery = true)
    Integer thongketype_payVNPAY(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.status_order = 0")
    Integer choxacnhan();

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.status_order = 1")
    Integer dangchuanbi();

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.status_order = 2")
    Integer danggiao();

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.status_order = 3")
    Integer thanhcong();

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.status_order = 4")
    Integer thatbai();

    @Query(value="sELECT count(b.id) as sodonhuy FROM  BillEntity b WHERE b.status_order = 5")
    Integer tuchoi();

//    @Query ( value="SELECT sum(hd.total) FROM Bill hd WHERE date_part('month', create_date) = :month and date_part('year', update_date) = :year" , nativeQuery = true)
//    BigDecimal thongkedoanhthu(@Param("month")Integer month, Integer year);

    @Query ( value="SELECT sum(hd.total) FROM Bill hd WHERE MONTH(hd.create_date) = :month and year(hd.create_date) = :year" , nativeQuery = true)
    BigDecimal thongkedoanhthu(@Param("month")Integer month, Integer year);

    @Query("select b from BillEntity b where :#{#bill.status_order} is null or b.status_order like :#{#bill.status_order} " +
            "and (:#{#bill.status_pay} is null or b.status_pay like :#{#bill.status_pay}) " +
            "and :#{#bill.date_start} is null and :#{#bill.date_end} is null or b.create_date between :#{#bill.date_start} and :#{#bill.date_end} " +
            " and b.name like %:#{#bill.p}% or b.id_code like %:#{#bill.p}% " +
            "order by b.status_order asc ")
    Page<BillEntity> filterBill(@Param("bill") BillParam param, Pageable pageable);

    @Query("select b from BillEntity b where b.status_pay like :#{#bill.status_pay} " +
            "and :#{#bill.date_start} is null and :#{#bill.date_end} is null or b.create_date between :#{#bill.date_start} and :#{#bill.date_end} " +
            " and b.name like %:#{#bill.p}% or b.id_code like %:#{#bill.p}% " +
            "order by b.status_order asc ")
    Page<BillEntity> filterBillPay(@Param("bill") BillParam param, Pageable pageable);

    @Query("select b from BillEntity b where b.create_date between :#{#bill.date_start} and :#{#bill.date_end} " +
            "order by b.status_order asc ")
    Page<BillEntity> filterBillDate(@Param("bill") BillParam param, Pageable pageable);

    @Query("select b from BillEntity b where b.create_date between :#{#bill.date_start} and :#{#bill.date_end} " +
            " and b.name like %:#{#bill.p}% or b.id_code like %:#{#bill.p}% " +
            "order by b.status_order asc ")
    Page<BillEntity> filterBillDateP(@Param("bill") BillParam param, Pageable pageable);

    @Query("select b from BillEntity b where b.name like %:#{#bill.p}% or b.id_code like %:#{#bill.p}% " +
            " order by b.status_order asc")
    Page<BillEntity> searchBill(@Param("bill") BillParam param, Pageable pageable);

    @Query("select b from BillEntity b where b.email=:email " +
            "and (:#{#bill.status_order} is null or b.status_order=:#{#bill.status_order})" +
            "and (:#{#bill.status_pay} is null or b.status_pay=:#{#bill.status_pay}) " +
            "and (:#{#bill.date_start} is null and :#{#bill.date_end} is null) " +
            "or(b.create_date between :#{#bill.date_start} and :#{#bill.date_end}) ")
    Page<BillEntity> findByEmail(@Param("email") String email, @Param("bill") BillParam param, Pageable pageable);



    //doanh thu
    @Query(value="sELECT * FROM  Bill b WHERE MONTH(update_date) = :month and YEAR(update_date)= :year" , nativeQuery = true)
    Page<BillEntity> findBill(@Param("month") Integer month, @Param("year") Integer year, Pageable pageable);

//    @Query(value = "select bill.update_date ,count(bill.id), sum(bill.total), sum(bill_detail.number) from Bill join bill_detail on bill.id = bill_detail.bill_id " +
//            "where bill.update_date between :open and :end GROUP BY bill.update_date order by ?#{#pageable}", nativeQuery = true)
//    Page<Object> soSpBandc(@Param("open") LocalDate open, @Param("end") LocalDate end, Pageable pageable);

    @Query(value = "select b.update_date ,count(b.id), sum(b.total), sum(bd.number) from BillEntity b join BillDetailEntity bd on b.id = bd.bill.id " +
            "where b.update_date between :open and :end GROUP BY b.update_date order by sum(b.total) DESC")
    Page<Object> soSpBandc1(@Param("open") LocalDate open, @Param("end") LocalDate end, Pageable pageable);


    @Query(value = "select b.update_date ,count(b.id), sum(b.total), sum(bd.number) from BillEntity b join BillDetailEntity bd on b.id = bd.bill.id " +
            "where b.update_date between :open and :end GROUP BY b.update_date order by sum(b.total) ASC")
    Page<Object> soSpBandc2(@Param("open") LocalDate open, @Param("end") LocalDate end, Pageable pageable);

    @Query(value = "select b.update_date ,count(b.id), sum(b.total), sum(bd.number) from BillEntity b join BillDetailEntity bd on b.id = bd.bill.id " +
            "where b.update_date between :open and :end GROUP BY b.update_date order by b.update_date ASC")
    Page<Object> soSpBandc3(@Param("open") LocalDate open, @Param("end") LocalDate end, Pageable pageable);

    @Query(value = "select b.update_date ,count(b.id), sum(b.total), sum(bd.number) from BillEntity b join BillDetailEntity bd on b.id = bd.bill.id " +
            "where b.update_date between :open and :end GROUP BY b.update_date order by b.update_date DESC")
    Page<Object> soSpBandc4(@Param("open") LocalDate open, @Param("end") LocalDate end, Pageable pageable);
}
