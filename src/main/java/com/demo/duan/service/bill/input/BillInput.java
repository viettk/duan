package com.demo.duan.service.bill.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class BillInput {

    @NotBlank(message = "Email Không được để trống!")
    @Email(message = "Email không đúng định dang")
    private String email;

    @NotBlank(message = "Tên không được để trống!")
    @Size(max = 100, message = "Tên Không được quá 100 ký tự")
    private String name;

    @NotBlank(message = "SĐT không được để trống!")
    @Positive(message = "SĐT phải là chữ số")
    private String phone;

    private Integer status_pay;

    @NotBlank(message = "Địa chỉ không được bỏ trống!")
    private String address;
    @NotBlank(message = "Tỉnh/thành phố không được để trống!")
    private String city;
    @NotBlank(message = "Quận/huyện không được để trống!")
    private String district;

    @NotBlank(message = "Phương/xã không được để trống!")
    private String wards;

    private BigDecimal total;

    private Integer status_order;

    private String describe;

    private String discountName;

    private Boolean type_pay;

    private String id_code;

//    private Integer staffId;
}
