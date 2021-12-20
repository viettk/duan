package com.demo.duan.service.mail;
import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.billdetail.BillDetailRepository;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private Environment env;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    BillRepository billRepository;
    @Autowired
    BillDetailRepository billDetailRepository;
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
    public void sendAll(MultipartFile file,String title,String content,Integer[] ids) throws MessagingException {
        List<CustomerEntity> customerEntities = customerRepository.findByIdIn(ids);
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

    @Override
    public void sendBill(MailEntity mail) throws MessagingException {
        // Tạo message
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String head = "";
        BillEntity bill = billRepository.getById(mail.getBillID());
        String mahd = bill.getId_code();
        String nguoinhan = bill.getName();
        String sdt = bill.getPhone();
        String diachi = bill.getWards() + " - " +bill.getDistrict() + " - " + bill.getCity();
        String ngaydat= bill.getCreate_date().getDayOfMonth() +"-"+bill.getCreate_date().getMonth().getValue()+"-"+bill.getCreate_date().getYear();
        String type = bill.getType_pay() ? "Thanh toán VNPAY" : "Thanh toán khi nhận hàng";
        head = head + " <p><b>Mã hóa đơn : </b>"+mahd+"</p>";
        head = head + " <p><b>Tên người nhận : </b>"+nguoinhan+"</p>";
        head = head + " <p><b>Số điện thoại : </b>"+sdt+"</p>";
        head = head + " <p><b>Địa chỉ : </b>"+diachi+"</p>";
        head = head + " <p><b>Ngày đặt : </b>"+ngaydat+"</p>";
        head = head + " <p><b>Kiểu thanh toán : </b>"+type+"</p>";
        head = bill.getType_pay()? (head + " <p><b>Trạng thái thanh toán : </b>Thanh toán thành công !</p>"): head;
        List<BillDetailEntity> listDetail= billDetailRepository.getListByCustomer(bill.getId());
        String table = "";
        for(BillDetailEntity b : listDetail){
            int i =1;
            table = table + "<tr>\n" +
                    "                <td>"+ i++ +"</td>\n" +
                    "                <td>"+b.getProduct().getSku()+"</td>\n" +
                    "                <td><img style=\"width: 100px;\" src=\"https://cdn.nguyenkimmall.com/images/detailed/555/may-anh-cho-nguoi-moi.jpg\" /></td>\n" +
                    "                <td>"+b.getProduct().getName()+"</td>\n" +
                    "                <td>"+b.getNumber()+"</td>\n" +
                    "                <td>"+currencyVN.format(b.getPrice())+"</td>\n" +
                    "                <td>"+currencyVN.format(b.getTotal())+"</td>\n" +
                    "            </tr>";
        }
        String magiamgia = bill.getDiscount()==null ? "Không" : bill.getDiscount().getName() + "Giảm " + currencyVN.format(bill.getDiscount().getValueDiscount());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(renderHtmlBill(bill.getTotal()+"",head,table,magiamgia), true);
        helper.setReplyTo(mail.getFrom());
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

    public String renderHtmlBill(String total , String head , String table , String magiamgia){
        return " <h2>Thông báo bạn vừa đặt hàng thành công tại Website Team7</h2>\n" +
                "    <p>Vui lòng đăng ký tài khoản trước khi đặt hàng để có thể theo dõi được đơn hàng và nhận nhiều ưu đãi khác</p>\n" +
                "    <h3>Thông tin hóa đơn</h3>\n" + head+
                "    <table border=\"1\">\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>STT</th>\n" +
                "                <th>Mã sản phẩm</th>\n" +
                "                <th>Ảnh</th>\n" +
                "                <th>Tên sản phẩm</th>\n" +
                "                <th>Số lượng</th>\n" +
                "                <th>Giá</th>\n" +
                "                <th>Tổng tiền</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "           \n" + table+
                "        </tbody>\n" +
                "        <tfoot>\n" +
                "            <th colspan=\"4\">Mã giảm giá áp dụng : "+magiamgia+"</th>\n" +
                "            <th colspan=\"4\">Thành tiền : "+total+"</th>\n" +
                "        </tfoot>\n" +
                "    </table>";
    }
}
