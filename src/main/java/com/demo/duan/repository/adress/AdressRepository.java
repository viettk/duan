package com.demo.duan.repository.adress;

import com.demo.duan.entity.AdressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface AdressRepository extends JpaRepository<AdressEntity, Integer> {
    long countAllByAddressAndCityAndDistrictAndCustomer_Id(String address, String city, String district, Integer customerId);

    long countAllByCustomer_IdAndCustomer_Email(Integer customer_id, String email);

    List<AdressEntity> findAllByCustomer_Id(Integer customer_id);

    @Query("from AdressEntity  a where a.customer.id = :customerId and a.customer.email = :email")
    List<AdressEntity> findAllList(Integer customerId, String email);

    @Query("from AdressEntity  a where a.status = false and a.customer.id = :customerId and a.customer.email = :email")
    AdressEntity findStatus(Integer customerId, String email);

    @Query("from AdressEntity a where a.id = :id and a.customer.email = :email")
    AdressEntity getOne(@Param("id") Integer id, @Param("email") String email);
}
