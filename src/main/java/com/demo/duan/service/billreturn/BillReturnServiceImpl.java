package com.demo.duan.service.billreturn;

import com.demo.duan.entity.*;
import com.demo.duan.repository.billreturn.BillReturnRepository;
import com.demo.duan.repository.billreturndetail.BillReturnDetailRepository;
import com.demo.duan.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BillReturnServiceImpl implements BillReturnService{

    private final BillReturnRepository resopsitory;

    private final BillReturnDetailRepository detailResopsitory;

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<Page<BillReturnEntity>> findAll(Pageable pageable) {
        Page<BillReturnEntity> result = resopsitory.findAll(pageable);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Page<BillReturnEntity>> findAll(String status, Pageable pageable) {
        Page<BillReturnEntity> result = resopsitory.findByStatus(status, pageable);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public BillReturnEntity returnBill(BillReturnEntity billReturn) throws RuntimeException{
        log.info("create bill return done!");
        Date date = new Date();
        billReturn.setCreate_date(date);
        billReturn.setStatus("Đơn hoàn trả");
        return resopsitory.save(billReturn);
    }

    @Override
    public BillReturnEntity updateBillReturn(Integer id, BillReturnEntity billReturn) throws RuntimeException{
        resopsitory.findById(id).orElseThrow(() -> new RuntimeException("Không tồn tại hóa đơn hoàn trả này"));
        Date date = new Date();
        billReturn.setConfirm_date(date);
        return resopsitory.save(billReturn);
    }

    @Override
    public BillReturnEntity confirmBillReturn(Integer id) {
        BillReturnEntity entity = resopsitory.findById(id).orElseThrow(() -> new RuntimeException("Không tồn tại hóa đơn hoàn trả này"));
        Date date = new Date();
        entity.setConfirm_date(date);
        entity.setStatus("Hoàn trả thành công");
        return resopsitory.save(entity);
    }

    @Override
    public BillReturnEntity undoBillReturn(Integer id) {
        BillReturnEntity entity = resopsitory.findById(id).orElseThrow(() -> new RuntimeException("Không tồn tại hóa đơn hoàn trả này"));
        List<BillReturnDetailEntity> billReturnDetailEntityList = detailResopsitory.findByBillReturn_Id(id);
        for (
                BillReturnDetailEntity x: billReturnDetailEntityList
        ) {
            ProductEntity product = productRepository.findById(x.getProduct().getId()).orElseThrow( () -> new RuntimeException("Không tồn tại sản phẩm này!"));
            product.setNumber(product.getNumber()-x.getReal_number());
            System.out.println("name: " + product.getName() + ", old number: " + product.getNumber()+", new number: "+(x.getNumber()+product.getNumber()));
            productRepository.save(product);
            x.setReal_number(0);
            detailResopsitory.save(x);
        }
        entity.setConfirm_date(null);
        entity.setStatus("Đơn hoàn trả");
        return resopsitory.save(entity);
    }

    @Override
    public BillReturnEntity totalBillReturn(Integer id) {
        BillReturnEntity entity = resopsitory.findById(id).orElseThrow(() -> new RuntimeException("Không tồn tại hóa đơn hoàn trả này"));
        BigDecimal tatol = detailResopsitory.totalBillRetrun(id);
        entity.setTotal(tatol);
        return resopsitory.save(entity);
    }

    // hoàn trả chi tiết từng sản phẩm

    @Override
    public List<BillReturnDetailEntity> getDetail(Integer id) {
        return detailResopsitory.findByBillReturn_Id(id);
    }

    @Override
    public BillReturnDetailEntity returnDetail(BillReturnDetailEntity billReturnDetail) {
        BigDecimal total = billReturnDetail.getPrice().multiply(new BigDecimal(billReturnDetail.getNumber()));
        billReturnDetail.setTotal(total);
        detailResopsitory.save(billReturnDetail);
        return billReturnDetail;
    }

    @Override
    public BillReturnDetailEntity returnDetailConfirm(Integer id, BillReturnDetailEntity billReturnDetail) {
        detailResopsitory.findById(id).orElseThrow(()-> new RuntimeException("Không thấy hóa đơn hoàn trả chi tiết này!"));
        detailResopsitory.save(billReturnDetail);
        ProductEntity product = productRepository.findById(billReturnDetail.getProduct().getId()).orElseThrow(()-> new RuntimeException("Không thấy sản phẩm này!"));
        product.setNumber(product.getNumber() + billReturnDetail.getReal_number());
        productRepository.save(product);
        return billReturnDetail;
    }

}
