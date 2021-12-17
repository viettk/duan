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
    @Pattern(regexp = "(\\+84|0)(90|93|120|121|122|126|128|96|97|98|167|164|166|163|165|168|169|91|92|94|123|124|125|127|129|88|89|86|99|3|2|9|8)[0-9]+"
    , message = "Số điện thoại không đúng định dạng")
    private String phone;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    private String city;

    private String district;

    private Boolean status;

    private Integer customerInput;

}
