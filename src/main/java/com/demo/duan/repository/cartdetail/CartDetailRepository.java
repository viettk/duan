package com.demo.duan.repository.cartdetail;

import com.demo.duan.entity.CartDetailEntity;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.param.CartDetailParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Integer> {

    @Query("from CartDetailEntity cd where cd.cart.customer.id = :customerId")
    List<CartDetailEntity> find(@Param("customerId") Integer customerId);

    @Query("from CartDetailEntity cd where cd.cart.customer.id = :customerId and cd.cart.customer.email= :email")
    List<CartDetailEntity> findCart(@Param("customerId") Integer customerId, @Param("email") String email);

    @Query("from CartDetailEntity cd where cd.cart.id = :cartId")
    List<CartDetailEntity> findListByCartId(@Param("cartId") Integer cartId);

    Optional<CartDetailEntity> findByCart_Id(Integer id);

    Integer countAllByCart_IdAndProduct_Id(Integer cartId, Integer productId);

    CartDetailEntity findByCart_IdAndProduct_Id(Integer cartId, Integer productId);

    @Query("select sum(cd.total) from CartDetailEntity cd where cd.cart.id = :cartId")
    BigDecimal totalOfCart(@Param("cartId") Integer cartId);

    @Query("select sum(cd.number) from CartDetailEntity cd where cd.cart.id = :cartId")
    Integer checkNumberOfCartDetail(@Param("cartId") Integer cartId);

    @Query("select count(cd.id) from CartDetailEntity cd where cd.cart.id = :cartId")
    Integer coutofCart(@Param("cartId") Integer cartId);

    void deleteAllByCart_Id(Integer cartId);

    @Query("select sum(cd.product.weight) from CartDetailEntity cd where cd.cart.id = :cartId")
    Float tinhTongCanNangCart(@Param("cartId") Integer cartId);

    @Query("select count(cd.id) from CartDetailEntity  cd where cd.cart.id = :cartid")
    Integer totalItemsCart(@Param("cartid") Integer cartid );

}
