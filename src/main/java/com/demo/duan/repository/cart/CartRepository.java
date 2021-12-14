package com.demo.duan.repository.cart;

import com.demo.duan.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    Optional<CartEntity> findByCustomer_Id(Integer id);

    @Query("from CartEntity cd where cd.customer.id = :id and cd.customer.email = :email")
    Optional<CartEntity> getByCustomer_IdAndEmail(Integer id, String email);

    @Query("from CartEntity cd where cd.customer.id = :id and cd.customer.email = :email")
    CartEntity getByCustomer_Id(Integer id, String email);

    @Query("from CartEntity cd where cd.customer.email = :email")
    CartEntity timCart(String email);
}
