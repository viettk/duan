package com.demo.duan.service.login_google;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GooglePojo {
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
    private String hd;
}
