package com.demo.duan.repository.receiptdetail;

import com.demo.duan.entity.ProductEntity;
import com.demo.duan.entity.ReceiptDetailEntity;
import com.demo.duan.entity.ReceiptEntity;
import com.demo.duan.service.product.param.ProductParam;
import com.demo.duan.service.receipt.param.ReceiptParam;
import com.demo.duan.service.receiptDetail.param.ReceiptDetailparam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetailEntity, Integer> {
    @Query("from ReceiptDetailEntity p where p.receipt.id = :receiptId " +
            "and (:#{#product.productName} is null or p.product.name = :#{#product.productName})" +
            "and (:#{#product.price} is null or p.product.price = :#{#product.price})")
    Page<ReceiptDetailEntity> searchByAdmin(Integer receiptId ,@Param("product") ReceiptDetailparam product, Pageable pageable );

    @Query("select r from ReceiptDetailEntity r where r.receipt=:id")
    Optional<ReceiptDetailEntity> findByIdRecript(Integer id);

    int countAllByReceipt_IdAndProduct_Id(Integer receiptId, Integer productid);

    int countAllByReceipt_Id(Integer receiptId);

    ReceiptDetailEntity findByReceipt_IdAndProduct_Id(Integer receiptId, Integer productid);

    @Query("select sum(cd.total) from ReceiptDetailEntity cd where cd.receipt.id = :cartId")
    BigDecimal totalOfCart(@Param("cartId") Integer cartId);

}
