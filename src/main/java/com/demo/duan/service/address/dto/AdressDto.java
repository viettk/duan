package com.demo.duan.service.address.dto;

import com.demo.duan.service.customer.dto.CustomerDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdressDto {

    private Integer id;

    private String name;

    private String phone;

    private String address;

    private String city;

    private String district;

//    private String wards;

    private Boolean status;

    private CustomerDto customer;

}
