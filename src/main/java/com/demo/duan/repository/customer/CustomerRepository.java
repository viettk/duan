package com.demo.duan.repository.customer;

import com.demo.duan.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity , Integer> {

    Integer countAllByEmail(String email);

    Optional<CustomerEntity> findByEmail(String email);

    @Query("select c from CustomerEntity c where c.email=:email")
    CustomerEntity getByEmail(@Param("email") String email);
}
