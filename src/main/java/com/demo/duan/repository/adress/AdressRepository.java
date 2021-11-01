package com.demo.duan.repository.adress;

import com.demo.duan.entity.AdressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdressRepository extends JpaRepository<AdressEntity, Integer> {
    long countAllByAddressAndCityAndDistrictAndCustomer_Id(String address, String city, String district, Integer customerId);

    long countAllByCustomer_Id(Integer customer_id);

    List<AdressEntity> findAllByCustomer_Id(Integer customer_id);
}
