package com.demo.duan.repository.customer;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.service.customer.paramcustomer.Customerparam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity , Integer> {

    Integer countAllByEmail(String email);

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByEmailAndStatusIsFalse(String email);

    Optional<CustomerEntity> findByIdAndEmail(Integer id, String email);
    
    CustomerEntity getByEmail(String email);

    @Query("from CustomerEntity c where (:#{#param.name} is null or c.name like %:#{#param.name}%)" +
            "and (:#{#param.email} is null or c.email like %:#{#param.email}%)" +
            "and (:#{#param.status} = false or c.status = :#{#param.status})")
    Page<CustomerEntity> getAll(@Param("param")Customerparam param, Pageable pageable);

    @Query("from CustomerEntity c where (:#{#name} is null or c.name like %:#{#name}%)" +
            "and (:#{#email} is null or c.email like %:#{#email}%)" +
            "and (:#{#status} is null or c.status = :#{#status})")
    Page<CustomerEntity> findAllCustomer(String email,Boolean status,String name,Pageable page);
}
