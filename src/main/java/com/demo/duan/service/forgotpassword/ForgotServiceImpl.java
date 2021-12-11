package com.demo.duan.service.forgotpassword;

import com.demo.duan.config.hashpassword.HashPass;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.forgotpassword.input.ChangePasswordInput;
import com.demo.duan.service.forgotpassword.input.ForgotPasswordInput;
import com.demo.duan.service.jwt.JwtTokenProvider;
import com.demo.duan.service.mail.MailEntity;
import com.demo.duan.service.mail.MailService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ForgotServiceImpl implements ForgotService{
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MailService mailService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private CustomerRepository customerRepository;
    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 3777700000L;
    @Value("${secrert.email}")
    private String JWT_SECRET;
    @Value("${secrert.login}")
    private String JWT_SECRET_LOGIN;

    @Override
    @Transactional
    public ResponseEntity<Object> getTokenEmail(Optional<String> email) throws MessagingException {
        //kiểm tra email rỗng
        Map<String, String> errors = new HashMap<>();
        if(email.get().isEmpty()) {
            errors.put("email", "Vui lòng nhập email");
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        boolean valid = EmailValidator.getInstance().isValid(email.get());
        if(valid == false){
            errors.put("email", "Email không đúng định dạng");
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        String username = null;
        // kiểm tra enail tồn tại thì gán vào username
        if(staffRepository.findByEmail(email.get()).isPresent()) {
            username = email.get();
        }else if(customerRepository.findByEmail(email.get()).isPresent()){
            username = email.get();
        }
        // nếu username rỗng
        if(username == null){
            errors.put("email", "Email không tồn tại trong hệ thống");
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        if(customerRepository.findByEmailAndStatusIsFalse(email.get()).isPresent()
                || staffRepository.findByEmailAndStatusIsFalse(email.get()).isPresent()){
            errors.put("email", "Tài khoản đã bị vô hiệu hóa");
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        String token  = jwtTokenProvider.generateToken(email.get(),JWT_EXPIRATION,JWT_SECRET);
        token = "http://localhost:3000/resetpassword/" + token ;
        mailService.send(new MailEntity(email.get(),"Bảo mật thông tin","Vui lòng click vào link dưới đây",token));
        return ResponseEntity.ok("Gửi email thành công ! Vui lòng kiểm tra hòm thư");
    }

    @Override
    @Transactional
    public ResponseEntity<Object> checkToken(Optional<String> token) {
        if (StringUtils.hasText(token.get()) && jwtTokenProvider.validateToken(token.get(),JWT_SECRET)) {
            // Lấy email user từ chuỗi jwt
            String email = jwtTokenProvider.getUserIdFromJWT(token.get(), JWT_SECRET);

            Optional<StaffEntity> staff = staffRepository.findByEmail(email);
            if (staffRepository.findByEmail(email).isPresent() || customerRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.ok(email);
            }else{
                throw new RuntimeException("Đường link không hợp lệ");
            }
        }else{
            throw new RuntimeException("Đường link không hợp lệ !");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> resetPassword(ForgotPasswordInput input) {
        if (StringUtils.hasText(input.getToken()) && jwtTokenProvider.validateToken(input.getToken(),JWT_SECRET)) {
            Map<String, String> errors = new HashMap<>();
            // Lấy email user từ chuỗi jwt
            String email = jwtTokenProvider.getUserIdFromJWT(input.getToken(), JWT_SECRET);
            if(input.getPassword().length()<6){
                errors.put("password", "Mật khẩu tối thiểu 6 ký tự");
            }
            if(input.getPassword().length()>15){
                errors.put("password", "Mật khẩu tối đa 15 ký tự");
            }
            if(!input.getPassword().equals(input.getRepeatPassword())){
                errors.put("repeatPassword", "Mật khẩu không khớp");
            }
            if(!errors.isEmpty()){
                return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
            }
            Optional<StaffEntity> staff = staffRepository.findByEmail(email);
            if (staff.isPresent()) {
                staff.get().setPassword(input.getPassword());
                staffRepository.save(staff.get());
                return ResponseEntity.ok("Đổi mật khẩu thành công !");
            }
            Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
            if (customer.isPresent()) {
                customer.get().setPassword(HashPass.hash(input.getPassword()));
                customerRepository.save(customer.get());
                return ResponseEntity.ok("Đổi mật khẩu thành công !");
            }
            throw new RuntimeException("Tài khoản không tồn tại trong hệ thống");
        }else{
            throw new RuntimeException("Đường link không hợp lệ !");
        }
    }

    @Override
    public ResponseEntity<Object> changePassword(ChangePasswordInput input) {
        Map<String, String> errors = new HashMap<>();
        // Lấy email user từ chuỗi jwt
        String email = jwtTokenProvider.getUserIdFromJWT(input.getToken(), JWT_SECRET_LOGIN);
        if(input.getNewPassword().length()<6){
            errors.put("newPassword", "Mật khẩu tối thiểu 6 ký tự");
        }
        if(input.getNewPassword().length()>15){
            errors.put("newPassword", "Mật khẩu tối đa 15 ký tự");
        }
        if(!input.getNewPassword().equals(input.getRepeatNewPassword())){
            errors.put("repeatNewPassword", "Nhập lại mật khẩu không chính xác");
        }
        Optional<StaffEntity> staff = staffRepository.findByEmail(email);
        if (staff.isPresent()) {
            if(!HashPass.hash(input.getPassword()).equals(staff.get().getPassword())){
                errors.put("password", "Mật khẩu cũ không đúng");
                return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
            }if(errors.isEmpty()) {
                staff.get().setPassword(HashPass.hash(input.getNewPassword()));
                staffRepository.save(staff.get());
                return ResponseEntity.ok("Đổi mật khẩu thành công !");
            }
        }
        Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            if(!HashPass.hash(input.getPassword()).equals(customer.get().getPassword())){
                errors.put("password", "Mật khẩu cũ không đúng");
                return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
            }if(errors.isEmpty()) {
                customer.get().setPassword(HashPass.hash(input.getNewPassword()));
                customerRepository.save(customer.get());
                return ResponseEntity.ok("Đổi mật khẩu thành công !");
            }
        }
        if(!errors.isEmpty()){
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        throw new RuntimeException("Tài khoản không tồn tại trong hệ thống");
    }
}
