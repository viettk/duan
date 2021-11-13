package com.demo.duan.repository.receipt;

import com.demo.duan.entity.ProductEntity;
import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.service.product.param.ProductParam;
import com.demo.duan.service.receipt.param.ReceiptParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Integer> {
    /* hiển thị Phiếu nhập*/
    @Query(value = "select * from Receipt where YEAR(create_date) = :year and MONTH(create_date) = :month", nativeQuery = true)
    Page<ReceiptEntity> searchByAdmin(@Param("month") Integer month, @Param("year") Integer year , Pageable pageable );
    /* search theo khoang ngay */
//    @Query("SELECT r FROM ReceiptEntity r WHERE r.create_date BETWEEN :getStartOfDay AND :getEndOfDay")
//    Page<ReceiptEntity> findDate(@Param("getStartOfDay") Date getStartOfDay ,@Param("getEndOfDay") Date getEndOfDay );
    /* search theo ngay */
    @Query("SELECt r FROM ReceiptEntity r WHERE r.create_date =:data_day")
    Optional<ReceiptEntity> findByDate(@Param("data_day") Date date);
}
