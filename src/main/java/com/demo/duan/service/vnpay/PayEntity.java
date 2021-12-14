package com.demo.duan.service.vnpay;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayEntity {
    private String orderInfo;
    private String amount;
    //    private String bankCode;
    private String phone;
    private String email;
    private String fullname;
    private String address;
    private String country;
    private String city;
}
