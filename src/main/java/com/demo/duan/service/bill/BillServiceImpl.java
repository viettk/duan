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

        /* l??u h??a ????n v??o m??y */
        BillEntity entity = mapper.inputToEntity(input);
        entity.setStatus_order(0);
        repository.save(entity);
        /* t???o h??a ????n chi ti???t */
        BillDetailInput billDetailInput = new BillDetailInput();
        billDetailInput.setBillId(entity.getId());

        /* D???a v??o login ????? l???y th??ng tin kh??ch h??ng -> l???y cartId */
        billDetailService.createByCustomer(billDetailInput, cartId);

        /* Set l???i th??nh ti???n cho h??a ????n */
//        BigDecimal totalOfBill =  billDetailService.totalOfBill(entity.getId());
//        entity.setTotal(totalOfBill);
//        repository.save(entity);

        /* X??a gi??? h??ng */
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

        /* l??u h??a ????n v??o m??y */
        BillEntity entity = mapper.inputToEntity(input);
        entity = repository.saveAndFlush(entity);
        if(input.getType_pay()){
            entity.setStatus_order(1);
        }else{
            entity.setStatus_order(1);
        }
        entity.setCreate_date(date);
        entity.setUpdate_date(date);

        /* Ki???m tra m?? gi???m gi?? c?? kh??? d???ng hay ko */
        if(!input.getDiscountName().equals("")){
            discount = discountRepository.searchDiscountByCustomer(input.getDiscountName())
                    .orElseThrow(()->new RuntimeException("M?? Gi???m gi?? kh??ng kh??? d???ng"));
            entity.setDiscount(discount);
            /* Tr??? m?? gi???m gi?? */
            discount.setNumber(discount.getNumber() - 1);
        }
        entity.setId_code(createCodeId(entity.getId()));
        entity.setTotal(input.getTotal());

        if(input.getDescribe().equals("")){
            entity.setDescribe("kh??ch ?????t");
        }
        repository.save(entity);

//        CreateBillPdf(entity.getId(), input.getName(), input.getEmail(), input.getPhone(), entity.getCreate_date(), entity.getTotal(), entity.getStatus_pay() );
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<BillDto> updateByCustomer(Integer id ,BillInput input) {
        BillEntity entity = repository.getById(id);

        /* N???u h??a ????n ??? c??c tr???ng th??i ??ang giao, giao th??nh c??ng , ???? h???y th?? ko c???p nh???t l???i ??c H??a ????n */
        if(entity.getStatus_order().equals("??ang giao h??ng") || entity.getStatus_order().equals("Giao th??nh c??ng")
        || entity.getStatus_order().equals("???? H???y")){
            throw new RuntimeException("B???n kh??ng th??? c???p nh???t H??a ????n");
        }

        /* C???p nh???t h??a ????n v?? l??u v??o db */
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
            Paragraph inlin_block = new Paragraph("\nTh??ng tin H??a ????n \n");

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

    @Override
    public ResponseEntity<BillDto> getOne(Integer id) {
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("H??a ????n n??y kh??ng t???n t???i"));
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<Page<BillDto>> getByEmail(String email, BillParam param, Pageable pageable) {
            Page<BillDto> result = this.repository.findByEmail(email, param, pageable).map(mapper :: entityToDto);
            return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<BillDto> update(BillInput input, Integer id) throws RuntimeException{
        BillEntity entity = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Kh??ng c?? h??a ????n n??y"));
        this.mapper.inputToEntity(input, entity);
        LocalDate date = LocalDate.now();
        entity.setUpdate_date(date);
        this.repository.save(entity);
        return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
    }

	@Override
	public ResponseEntity<BillDto> updateStatusOder(Integer id, BillInput input) throws RuntimeException{
		BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("????n h??ng n??y kh??ng t???n t???i!"));

		Integer status = null;

		LocalDate date = LocalDate.now();
		switch (input.getStatus_order()){
	        case 0:
	            status = 0;
	            break;
	        case 1:
	            status = 1;
	            break;
	        case 2:
	            status = 2;
	            break;
	        case 3:
	            status = 3;
	            break;
            case 4:
                status = 4;
                break;
            case 5:
                status = 5;
                break;
	        default:
	            throw new RuntimeException("Kh??ng c?? tr???ng th??i n??y, vui l??ng c???p nh???t l???i");
	    }
		Integer status_pay = null;
		if(input.getStatus_pay().equals("")) {
			status_pay = entity.getStatus_pay();
		}else {
			status_pay = input.getStatus_pay();
		}
		entity.setStatus_pay(status_pay);
		entity.setStatus_order(status);
		entity.setUpdate_date(date);
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}
	@Override
	public ResponseEntity<BillDto> updateStatusPay(Integer id, BillInput input) {
		BillEntity entity = this.repository.findById(id).orElseThrow( () ->  new RuntimeException("????n h??ng n??y kh??ng t???n t???i!"));
        Integer status = null;
        LocalDate date = LocalDate.now();
        switch (input.getStatus_order()){
            case 0:
                status = 0;
                break;
            case 1:
                status = 1;
                break;
            case 2:
                status = 2;
                break;
            default:
                throw new RuntimeException("Kh??ng c?? tr???ng th??i n??y, vui l??ng c???p nh???t l???i");
        }
		entity.setUpdate_date(date);
		entity.setStatus_pay(status);
		this.repository.save(entity);
		return ResponseEntity.ok().body(this.mapper.entityToDto(entity));
	}

    @Override
    public ResponseEntity<Page<BillDto>> filterBill(BillParam param, Pageable pageable) {
        Page<BillDto> result = repository.filterBill(param, pageable).map( mapper :: entityToDto);
        return ResponseEntity.ok().body(result);
    }

}
