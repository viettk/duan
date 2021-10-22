package com.demo.duan.config.hashpassword;

import org.mindrot.jbcrypt.BCrypt;

public class HashPass {

    /* phục vụ cho khách hàng đăng kí */
    /* plain là mật khẩu khách hàng nhập */
    public static String hash(String plain){
        /* Mã hóa mật khẩu vừa nhập */
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(plain, salt);
    }

    /* phục vụ cho khách hàng đăng nhập */
    public static boolean verify(String plain, String hashed){
        return BCrypt.checkpw(plain, hashed);
    }
}
