package com.demo.duan;

import com.demo.duan.service.staff.StaffService;
import com.demo.duan.service.staff.input.StaffInput;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DuanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuanApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(StaffService service){
//        return args -> {
//          service.createStaff(new StaffInput("admin@gmail.com", "12345","token", "thuan", "ROLE_ADMIN", true, "0987456231"));
//        };
//    }
}
