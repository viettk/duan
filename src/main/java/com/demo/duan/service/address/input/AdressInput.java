package com.demo.duan.service.address.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AdressInput {

    @NotBlank(message = "Họ tên không được để trống")
    private String name;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "(\\+84|0)(90|93|070|079|077|076|078|96|97|98|037|034|036|033|035|038|039|91|92|94|083|084|085|087|089|88|89|86|99|3|2|9|8)[0-9]+"
            , message = "Số điện thoại không đúng định dạng")
    private String phone;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    private String city;

    private String district;

    private Boolean status;

    private Integer customerInput;

}
