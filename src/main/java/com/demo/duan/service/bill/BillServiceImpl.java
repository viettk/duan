package com.demo.duan.service.bill;

import com.demo.duan.entity.*;
import com.demo.duan.repository.adress.AdressRepository;
import com.demo.duan.repository.bill.BillRepository;
import com.demo.duan.repository.billdetail.BillDetailRepository;
import com.demo.duan.repository.cart.CartRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.repository.discount.DiscountRepository;
import com.demo.duan.service.address.AdressService;
import com.demo.duan.service.bill.dto.BillDto;
import com.demo.duan.service.bill.input.BillInput;
import com.demo.duan.service.bill.mapper.BillMapper;
import com.demo.duan.service.bill.param.BillParam;
import com.demo.duan.service.billdetail.BillDetailService;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import com.demo.duan.service.billdetail.mapper.BillDetailMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService{

    private final BillRepository repository;

    private  final BillMapper mapper;

    private final BillDetailService billDetailService;

    private final CartDetailRepository cartDetailRepository;

    private final DiscountRepository discountRepository;

    private final AdressService dressService;

    private final CustomerRepository customerRepository;

    private final CartRepository cartRepository;

    private final AdressRepository adressRepository;

    private final BillDetailRepository billDetailRepository;

    private final BillDetailMapper billDetailMapper;

    @Override
    @Transactional
    public ResponseEntity<BillDto> createByCustomer(Integer cartId ,BillInput input) {
        LocalDate date = LocalDate.now();
        DiscountEntity discount = new DiscountEntity();

        /* lưu hóa đơn vào máy */
        BillEntity entity = mapper.inputToEntity(input);
        entity = repository.saveAndFlush(entity);
        if(input.getType_pay()){
            entity.setStatus_order(1);
        }else {
            entity.setStatus_order(1);
        }

        entity.setCreate_date(date);
        entity.setUpdate_date(date);
        entity.setTotal(input.getTotal());

        /* Kiểm tra mã giảm giá có khả dụng hay ko */
        if(!input.getDiscountName().equals("")){
            discount = discountRepository.getByName(input.getDiscountName());
            entity.setDiscount(discount);
            /* Trừ mã giảm giá */
            discount.setNumber(discount.getNumber() - 1);
        }
        if(input.getDescribe().equals("")){
            entity.setDescribe("khách đặt");
        }

        entity.setId_code(createCodeId(entity.getId()));
        repository.save(entity);
        /* tạo hóa đơn chi tiết */
        BillDetailInput billDetailInput = new BillDetailInput();
        billDetailInput.setBillId(entity.getId());

        /* Dựa vào login để lấy thông tin khách hàng -> lấy cartId */
        billDetailService.createByCustomer(billDetailInput, cartId);

        /* Set lại thành tiền cho hóa đơn */
//        BigDecimal totalOfBill =  billDetailService.totalOfBill(entity.getId());
//        entity.setTotal(totalOfBill);
//        repository.save(entity);

        /* Xóa giỏ hàng */
        cartDetailRepository.deleteAllByCart_Id(cartId);
        CartEntity cartEntity = cartRepository.getById(cartId);
        cartEntity.setTotal(BigDecimal.ZERO);

        return ResponseEntity.ok().body(mapper.entityToDto(entity));

    }

    @Override
    @Transactional
    public ResponseEntity<BillDto> createByCustomerNotLogin(BillInput input) {

        LocalDate date = LocalDate.now();
        DiscountEntity discount = new DiscountEntity();

        /* lưu hóa đơn vào máy */
        BillEntity entity = mapper.inputToEntity(input);
        entity = repository.saveAndFlush(entity);
        if(input.getType_pay()){
            entity.setStatus_order(1);
        }else{
            entity.setStatus_order(1);
        }
        entity.setCreate_date(date);
        entity.setUpdate_date(date);

        /* Kiểm tra mã giảm giá có khả dụng hay ko */
        if(!input.getDiscountName().equals("")){
            discount = discountRepository.searchDiscountByCustomer(input.getDiscountName())
                    .orElseThrow(()->new RuntimeException("Mã Giảm giá không khả dụng"));
            entity.setDiscount(discount);
            /* Trừ mã giảm giá */
            discount.setNumber(discount.getNumber() - 1);
        }
        entity.setId_code(createCodeId(entity.getId()));
        entity.setTotal(input.getTotal());

        if(input.getDescribe().equals("")){
            entity.setDescribe("khách đặt");
        }
        repository.save(entity);

//        CreateBillPdf(entity.getId(), input.getName(), input.getEmail(), input.getPhone(), entity.getCreate_date(), entity.getTotal(), entity.getStatus_pay() );
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input) {
        BillEntity entity = repository.getById(id);

        /* Nếu hóa đơn ở các trạng thái đang giao, giao thành công , Đã hủy thì ko cập nhật lại đc Hóa đơn */
        if(entity.getStatus_order().equals("Đang giao hàng") || entity.getStatus_order().equals("Giao thành công")
        || entity.getStatus_order().equals("Đã Hủy")){
            throw new RuntimeException("Bạn không thể cập nhật Hóa đơn");
        }

        /* Cập nhật hóa đơn và lưu vào db */
        mapper.inputToEntity(input, entity);
        repository.save(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<Page<BillDto>> getCustomerId(String email, Optional<Integer> page, Optional<Integer> limit) {
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), Sort.by(Sort.Direction.DESC, "id"));
        Page<BillDto> dtos = repository.getBillCustomer(email, pageable).map(mapper::entityToDto);
        return ResponseEntity.ok().body(dtos);
    }

    @Override
    @Transactional
    public ResponseEntity<List<BillDetailDto>> getBillDetailCustomer(Integer billId) {
        List<BillDetailEntity> entities = billDetailRepository.getListByCustomer(billId);
        List<BillDetailDto> dtos = billDetailMapper.EntitiesToDtos(entities);
        return ResponseEntity.ok().body(dtos);
    }

    @Override
    @Transactional
    public Integer getDonHuy(Integer month, Integer year) {
        return repository.donhuy(month, year);
    }

    @Override
    @Transactional
    public Integer getDonTra(Integer month, Integer year) {
        return repository.dontra(month, year);
    }

    @Override
    @Transactional
    public Integer getDonTc(Integer month, Integer year) {
        return repository.dontc(month, year);
    }

    @Override
    public Object sanPhambanchy(Integer month, Integer year) {
        return null;
    }

    @Override
    @Transactional
    public List<BigDecimal> thongkedoanhthu(Integer year) {
        List<BigDecimal> lst = new ArrayList<>();
        for(int i =0; i<= 12 ;i++){
            BigDecimal num = repository.thongkedoanhthu(i, year);
            if(num == null){
                num = BigDecimal.ZERO;
            }
            lst.add(num);
        }
        return lst;
    }


    @Scheduled(cron="0 0 0 1 * ?")
    public void reloadId(int num){
        num= 1;
    }

    /* tao id_code */
    private String createCodeId(Integer id_count){
        int num = 0;
        reloadId(num);
        String id_code = "";
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for(num = 1; num < id_count; num++){
            id_code ="HD" +localDate.getYear() +"-" + localDate.getMonthValue()+ "-"+num;
        }
        return id_code;
    }

    /* bill admin*/


    @Override
    public ResponseEntity<BillDto> getOne(Integer id) {
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn này không tồn tại"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<Page<BillDto>> getByEmail(String email, BillParam param, Pageable pageable) {
        Page<BillDto> result = this.repository.findByEmail(email, param, pageable).map(mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
    }


    @Override
    public ResponseEntity<BillDto> update(BillInput input, Integer id) throws RuntimeException{
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Không có hóa đơn này"));
        this.mapper.inputToEntity(input, entity);
        LocalDate date = LocalDate.now();
        entity.setUpdate_date(date);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input) throws RuntimeException{
//        BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("Đơn hàng này không tồn tại!"));
//        String status = "";
//
//        LocalDate date = LocalDate.now();
//
//        switch (input.getStatus_order()){
//            case 1:
//                status = 1;
//                break;
//            case "Đang chuẩn bị hàng":
//                status = "Đang chuẩn bị hàng";
//                break;
//            case "Đang giao hàng":
//                status = "Đang giao hàng";
//                break;
//            case "Hoàn thành":
//                status = "Hoàn thành";
//                break;
//            case "Thất bại":
//                status = "Thất bại";
//                break;
//            case "Đã hủy":
//                status = "Đã hủy";
//                break;
//            case "Giao hàng thành công":
//                status = "Giao hàng thành công";
//                break;
//            case "Đơn hoàn trả":
//                status = "Đơn hoàn trả";
//                break;
//            default:
//                throw new RuntimeException("Không có trạng thái này, vui lòng cập nhật lại");
//        }
//
//        String status_pay = "";
//        if(input.getStatus_pay().equals("")) {
//            status_pay = entity.getStatus_pay();
//        }else {
//            status_pay = input.getStatus_pay();
//        }
//        entity.setStatus_pay(status_pay);
//        entity.setStatus_order(status);
//        entity.setUpdate_date(date);
//        this.repository.save(entity);
//        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
        return null;
    }
    @Override
    public ResponseEntity<BillDto> updateStatusPay(Integer id, BillInput input) {
//        BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("Đơn hàng này không tồn tại!"));
//        String status = "";
//        LocalDate date = LocalDate.now();
//        switch (input.getStatus_order()){
//            case "Đã thanh toán":
//                status = "Đã thanh toán";
//                break;
//            case "Đã hoàn trả":
//                status = "Đã hoàn trả";
//                break;
//            case "Chưa thanh toán":
//                status = "Chưa thanh toán";
//                break;
//            case "Hủy":
//                status = "Thanh toán online";
//                break;
//            default:
//                throw new RuntimeException("Không có trạng thái này, vui lòng cập nhật lại");
//        }
//        entity.setUpdate_date(date);
//        this.repository.save(entity);
//        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
        return null;
    }

    @Override
    public ResponseEntity<Page<BillDto>> filterBill(BillParam param, Pageable pageable) {
        Page<BillDto> result = repository.filterBill(param, pageable).map( mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
    }


    @Override
    @Transactional
    public void CreateBillPdf(Integer billId ,String name, String email, String phone, LocalDate date, BigDecimal totalBillMoney, String statusPay) {
        try {
            //Create Document instance.
            Document document = new Document();

            //Create OutputStream instance.
            OutputStream outputStream =
                    new FileOutputStream(new File("D:\\TestParagraphFile.pdf"));

            //Create PDFWriter instance.
            PdfWriter.getInstance(document, outputStream);

            //Open the document.
            document.open();

            //Create Paragraph objects
            Paragraph paragraph1 = new Paragraph("Hoa don mua hang");
            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph paragraph2 = new Paragraph("Thong tin khach hang");
            Paragraph infor      = new Paragraph("Ho ten: "+ name + "\nEmail: "+ email
                    + "\nSDT: " + phone );
            Paragraph dateBuy = new Paragraph("Ngay mua: " + date );
            Paragraph inlin_block = new Paragraph("\nThông tin Hóa đơn \n");

            //Add content to the document using Paragraph objects.
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(infor);
            document.add(inlin_block);

            List<BillDetailEntity> lstBillDetailEntities = billDetailRepository.getListByCustomer(billId);

            //add table
            PdfPTable table = new PdfPTable(5);
            Stream.of("Ma SKU", "Ten San pham", "Gia", "So luong", "Tong tien").forEach(
                    columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });

            for (BillDetailEntity b : lstBillDetailEntities) {
                table.addCell(String.valueOf(b.getId()));
                table.addCell(b.getProduct().getSku());
                table.addCell(b.getProduct().getName());
                table.addCell(String.valueOf(b.getPrice()));
                table.addCell(String.valueOf(b.getNumber()));
                table.addCell(String.valueOf(b.getTotal()));
            }

            Paragraph total = new Paragraph(String.valueOf(totalBillMoney));

            Paragraph status = new Paragraph(statusPay);

            //Close document and outputStream.
            document.add(table);
            document.add(total);
            document.add(status);
            document.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<BillDto> changeStatus_pay(Integer id, String status_pay) {
        BillEntity billEntity = repository.getById(id);
//        billEntity.setStatus_pay(status_pay);
        repository.save(billEntity);
        BillDto billDto = mapper.entityToDto(billEntity);
        return ResponseEntity.ok().body(billDto);
    }
}
