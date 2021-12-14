package com.demo.duan.service.jwt;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.staff.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements UserDetailsService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    PasswordEncoder pe;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        CustomerEntity customer = customerRepository.findByEmail(email).orElse(null);
        StaffEntity staff = staffRepository.findByEmail(email).orElse(null);
        if (staff == null && customer == null) {
            throw new UsernameNotFoundException(email);
        }
        if (staff != null) {
            staff.setPassword(pe.encode(staff.getPassword()));
            return new StaffUserDetails(staff);
        }
        customer.setPassword(pe.encode(customer.getPassword()));
        return new CustomUserDetails(customer);
    }
}
