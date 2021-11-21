package com.demo.duan.service.bill.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BillInput {

    @NotBlank(message = "Không được để trống!")
    @Email(message = "Email không đúng định dang")
    private String email;

    @NotBlank(message = "Tên không được để trống!")
    @Size(min = 1, max = 100)
    private String name;

    @NotNull(message = "SDDT không được để trống!")
    @Positive(message = "SĐT phải là chữ số")
    private String phone;

    private String status_pay;

    @NotNull(message = "Địa chỉ không được bỏ trống!")
    private String address;
    @NotBlank(message = "Tỉnh/thành phố không được để trống!")
    private String city;
    @NotBlank(message = "Quận/huyện không được để trống!")
    private String district;

    @NotBlank(message = "Phương/xã không được để trống!")
    String wards;

    private String status_order;

    private String describe;

    private String thema;

    private String themb;

    private String themc;

    private String discountName;

//    private Integer staffId;
}
