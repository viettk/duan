package com.demo.duan.service.vnpay;

import com.demo.duan.entity.BillEntity;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.mail.MailEntity;
import com.demo.duan.service.mail.MailService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;


@Service
public class VNPayServiceImpl implements VNPayService{
    @Autowired
    BillRepository billRepository;
    @Autowired
    MailService mailService;
    @Override
    public ResponseEntity<Object> pay(HttpServletRequest request, BillDto dto) throws IOException {
        String vnp_Version = "2.1.0";//Phiên bản api mà khách hàng kết nối
        String vnp_Command = "pay";//Mã API sử dụng, mã cho giao dịch thanh toán là: pay
        String vnp_OrderInfo ="thanhtoanvnpay";//Thông tin mô tả nội dung thanh toán;
        String orderType = "250000"; //Mã danh mục hàng hóa. Mỗi hàng hóa sẽ thuộc một nhóm danh mục do VNPAY quy định
        String vnp_TxnRef = dto.getId()+""; // Mã tham chiếu của giao dịch không được trùng lặp trong ngày Mã này là duy nhất dùng để phân biệt các đơn hàng gửi sang VNPAY
        String vnp_IpAddr = Config.getIpAddress(request);//lấy địa chỉ ip
        String vnp_TmnCode = Config.vnp_TmnCode;;//Mã website trên hệ thống của VNPAY
        int amount = Math.round(Integer.parseInt( String.valueOf(dto.getTotal()))/1000) * 100*1000;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");//Đơn vị tiền tệ sử dụng thanh toán
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");//Ngôn ngữ giao diện hiển thị
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);// giao diện kết quả trả về
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());// thời gian hết hạn
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        vnp_Params.put("vnp_Inv_Type", "hoạt động");
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);//Sắp xếp tăng dần
        StringBuilder hashData = new StringBuilder();//StringBuilder trong Java được sử dụng để tạo chuỗi có thể thay đổi
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();// là một interface được sử dụng để thay thế Enumerations(bảng kê) trong Java Collection
        while (itr.hasNext()) {//Trả về true nếu có phần tử tiếp theo. Nếu không, trả về false.
            String fieldName = (String) itr.next();//Nó trả về phần tử hiện tại và di chuyển con trỏ trỏ tới phần tử tiếp theo.
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                Tham số đầu tiên là văn bản để mã hóa; thứ hai là tên của mã hóa ký tự sẽ sử dụng (ví dụ: UTF-8)
                //ASCII bảy bit, hay còn gọi là ISO646-US, hay còn gọi là khối Latinh Cơ bản của bộ ký tự Unicode
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        return ResponseEntity.ok(paymentUrl);
    }

    @Override
    public Map<String ,Object> find(String time, Integer id , String ip) throws IOException {
        //vnp_Command = querydr
        String vnp_TxnRef = id +"";
        String vnp_TransDate = time;
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_IpAddr = ip;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "querydr");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
        vnp_Params.put("vnp_TransDate", vnp_TransDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_apiUrl + "?" + queryUrl;
        URL url = new URL(paymentUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String Rsp = response.toString();
        String respDecode = URLDecoder.decode(Rsp, "UTF-8");
        String[] responseData = respDecode.split("&");
        Map<String,Object> billPay = new HashMap<>();
        for(String x : responseData){
            String[] d = x.split("=");
            billPay.put(d[0],d[1]);
        }
        return billPay;
    }

    @Override
    public ResponseEntity<Object> refund(HttpServletRequest request) throws IOException {
        //vnp_Command = refund
        String vnp_TxnRef = request.getParameter("order_id");
        String vnp_TransDate = request.getParameter("trans_date");
        String email = request.getParameter("email");
        int amount = Integer.parseInt(request.getParameter("amount"))*100;
        String trantype = request.getParameter("trantype");
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_IpAddr = Config.getIpAddress(request);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "refund");
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
        vnp_Params.put("vnp_TransDate", vnp_TransDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateBy", email);
        vnp_Params.put("vnp_TransactionType", trantype);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret , hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_apiUrl + "?" + queryUrl;
        URL url = new URL(paymentUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String Rsp = response.toString();
        String respDecode = URLDecoder.decode(Rsp, "UTF-8");
        String[] responseData = respDecode.split("&|\\=");
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("data", Arrays.toString(responseData));
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
        return ResponseEntity.ok(null);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> check(HttpServletRequest request) throws IOException {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0))
            {
                fields.put(fieldName, fieldValue);
            }
        }
        String vnp_SecureHash = (String) fields.get("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType"))
        {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash"))
        {
            fields.remove("vnp_SecureHash");
        }
        Boolean status =true;
        Optional<BillEntity> bill = billRepository.findById(Integer.valueOf(fields.get("vnp_TxnRef")+""));
        // check mã hóa đơn có tồn tại trong db
        if(bill.isEmpty()){
            status=false;
        }
        //check tổng tiền trong db có bằng nhau không
        if(bill.get().getTotal().compareTo(BigDecimal.valueOf(Long.valueOf((String) fields.get("vnp_Amount")) / 100))!=0){
            status=false;

        }
        //Check trạng thái
        if(!fields.get("vnp_ResponseCode").equals("00")){
            status=false;
        }
        String signValue = Config.hashAllFields(fields);
        //check ma bam
        if(!signValue.equals(vnp_SecureHash)){
            status=false;
        }
        if(status){
            String ip = Config.getIpAddress(request);
            Integer id = bill.get().getId();
            String time = (String) fields.get("vnp_PayDate");
            Map<String,Object> order= find(time,id,ip);
            if(order.get("vnp_ResponseCode").equals("00")){
                bill.get().setStatus_pay(1);
                billRepository.save(bill.get());
                try {
                    mailService.sendBill(new MailEntity(bill.get().getEmail(),"Thông báo đặt hàng thành công","",bill.get().getId()));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseEntity.ok(status);
    }
}
