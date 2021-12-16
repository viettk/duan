package com.demo.duan.service.mail;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private Environment env;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    CustomerRepository customerRepository;
    List<MimeMessage> queue = new ArrayList<>();
    @Override
    public void send(MailEntity mail) throws MessagingException {
        // Tạo message
        MimeMessage message = javaMailSender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(renderHtml(mail.getToken()), true);
//        message.setContent(renderHtml(mail.getToken()), "text/html");
        helper.setReplyTo(mail.getFrom());
//        if(!mail.getBcc().isEmpty()) {
//            for (String email : mail.getBcc()) {
//                helper.addBcc(email);
//            }
//        }
//         file đính kèm
//        String[] attachments = mail.getAttachments();
//        if(attachments != null && attachments.length > 0) {
//            for(String path: attachments) {
//                MultipartFile file =null;
//                helper.addAttachment(file.getName(), file);
//            }
//        }
        // Gửi message đến SMTP server
        javaMailSender.send(message);
    }

    @Override
    @Transactional
    public void sendAll(MultipartFile file,String title,String content) throws MessagingException {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        List<String> bcc = new ArrayList<String>();
        for(CustomerEntity customer : customerEntities){
            bcc.add(customer.getEmail());
        }
        MimeMessage message = javaMailSender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setTo(env.getProperty("spring.mail.username"));
        helper.setSubject("Thông báo WebSite BanDai");
        helper.setText(renderAllHtml(title,content), true);
//        message.setContent(renderHtml(mail.getToken()), "text/html");
        helper.setReplyTo(env.getProperty("spring.mail.username"));
        if(!bcc.isEmpty()) {
            for (String email : bcc) {
                helper.addBcc(email);
            }
        }
//         file đính kèm
//        String[] attachments = mail.getAttachments();
        if(file != null) {
            helper.addAttachment(file.getOriginalFilename(), file);
        }
        // Gửi message đến SMTP server
        javaMailSender.send(message);
    }


//    @Scheduled(fixedDelay = 1000)
//    public void run() {
//        while(!queue.isEmpty()) {
//            System.out.println("send mail");
//            MimeMessage message = queue.remove(0);
//            javaMailSender.send(message);
//        }
//    }

    public String renderHtml(String token){
        return "<div style=\"margin:0 30%; text-align: center; display: block; font-family:Roboto;\">\n" +
                "        <h1 style=\"margin-bottom: 20px; text-align: left;\">Đặt lại mật khẩu tài khoản BanDai</h1>\n" +
                "        <p style=\"text-align: left;\">Bạn chỉ cần click vào nút <b>xác nhận</b> dưới đây để đặt lại mật khẩu: <b>(Nó chỉ có tác dụng trong 3 phút)</b></p>\n" +
                "        <a href=\""+token+"\" style=\"margin: 24px 0;line-height: 48px;padding: 11px 30px; background: linear-gradient(\n" +
                "        180deg,#FF6666 0%,#f0b90b 100%); border-radius: 4px;font-weight: bold;color: #1e2026; text-decoration: none;\">Xác nhận</a>\n" +
                "        <hr><p style=\"text-align: left;\">Bạn đang gặp sự cố? Nếu bạn không thể đặt lại mật khẩu hoặc bạn chưa thử đăng nhập, vui lòng liên hệ với bộ phận hỗ trợ .</p>\n" +
                "    </div>";
    }

    public String renderAllHtml(String title, String content){
        return "<div style=\"margin:0 30%; text-align: center; display: block; font-family:Roboto;\">\n" +
                "        <h1 style=\"margin-bottom: 20px; text-align: left;\">"+title+"</h1>\n" +
                "        <p style=\"text-align: left;\">"+ content +"</p>\n" +
                "        <hr><p style=\"text-align: left;\">Bạn đang gặp sự cố? Nếu bạn không thể đặt lại mật khẩu hoặc bạn chưa thử đăng nhập, vui lòng liên hệ với bộ phận hỗ trợ .</p>\n" +
                "    </div>";
    }
}
