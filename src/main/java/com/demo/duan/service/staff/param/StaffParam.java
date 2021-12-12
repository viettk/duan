package com.demo.duan.service.staff.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StaffParam {

    private String email;

    private String name;

    private Boolean status;

    private Integer role;

}
