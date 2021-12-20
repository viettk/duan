package com.demo.duan.service.cartdetail;

import com.demo.duan.entity.*;
import com.demo.duan.repository.cart.CartRepository;
import com.demo.duan.repository.cartdetail.CartDetailRepository;
import com.demo.duan.repository.product.ProductRepository;
import com.demo.duan.service.billdetail.dto.BillDetailDto;
import com.demo.duan.service.billdetail.input.BillDetailInput;
import com.demo.duan.service.cartdetail.dto.CartDetailDto;
import com.demo.duan.service.cartdetail.input.CartDetailInput;
import com.demo.duan.service.cartdetail.input.CartDetalInputDelete;
import com.demo.duan.service.cartdetail.mapper.CartDetailMapper;
import com.demo.duan.service.cartdetail.param.CartDetailParam;
import com.demo.duan.service.product.dto.ProductDto;
import com.demo.duan.service.product.param.ProductParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartDetailServiceImpl implements CartDetailService{

    private final CartDetailRepository repository;

    private final CartDetailMapper mapper;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;


    @Override
    @Transactional
    public ResponseEntity<List<CartDetailDto>> find(Integer customerId, String email) {
        List<CartDetailEntity> lst = repository.findCart(customerId, email);
        List<CartDetailDto> lstDto =mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(lstDto);
    }

    @Override
    @Transactional
    public ResponseEntity<CartDetailDto> addToCartDetail(Integer customerId, String email, CartDetailInput input) {
        /* Lấy thông tin sản phẩm */
        ProductEntity product = productRepository.getById(input.getProductId());
        /* Lấy thông tin Giỏ hàng */
        CartEntity cartEntity = cartRepository.getByCustomer_Id(customerId, email);

        /* Lấy giá sản phẩm */
        BigDecimal price = product.getPrice();
        BigDecimal total= BigDecimal.ZERO;

        /* Kiểm tra trong kho có đủ sp ko */
//        if(product.getNumber() < input.getNumber()){
//            throw new RuntimeException("Sản phẩm không đủ");
//        }

        /* Kiểm tra sản phẩm đã có sẵn trong Giỏ hàng chưa */
        Integer count = repository.countAllByCart_IdAndProduct_Id(cartEntity.getId(), input.getProductId());

        /* Nếu có r thì cập nhật số lượng và tổng tiền */
        if(count > 0){
            /* Lấy thông tin giỏ hàng chi tiết */
            CartDetailEntity entity = repository.findByCart_IdAndProduct_Id(cartEntity.getId(), input.getProductId());
            /* Tăng số lượng trong giỏ hàng chi tiết */
            int number = input.getNumber() ;
            Integer checkNumOfCartDetail = repository.checkNumberOfCartDetail(cartEntity.getId());

            int newNum = number + entity.getNumber();
            /* Tính lại tổng tiền từng sản phẩm */
            total = price.multiply(BigDecimal.valueOf(newNum));
            mapper.inputToEntity(input, entity);

            /* Lưu thông tin giỏ hàng chi tiết */
            entity.setTotal(total);
            entity.setNumber(newNum);
            repository.save(entity);
            totalOfCart(cartEntity.getId(), email);
            return ResponseEntity.ok().body(mapper.entityToDto(entity));
        }

        /* Thêm sản phẩm mới vào giỏ */
        else{
            /* Kiểm tra số lượng sp trong giỏ hàng phải <=15 */
            Integer checkNumberOfCartDetail = repository.checkNumberOfCartDetail(cartEntity.getId());
            if(checkNumberOfCartDetail == null){
                checkNumberOfCartDetail =0;
            }

            /* input -> entity */
            CartDetailEntity entity = mapper.inputToEntity(input);
            entity.setProduct(product);
            entity.setCart(cartEntity);

            /* Tính tổng tiền sản phẩm vừa thêm vào */
            total= price.multiply(BigDecimal.valueOf(input.getNumber()));

            /* Lưu giỏ hàng chi tiết */
            entity.setTotal(total);
            repository.save(entity);
            totalOfCart(cartEntity.getId(), email);
            return ResponseEntity.ok().body(mapper.entityToDto(entity));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<CartDetailDto> updateNumberUp(Integer customerId, String email,CartDetailInput input) {
        CartEntity cartEntity = cartRepository.getByCustomer_Id(customerId, email);
        CartDetailEntity entity = repository.findByCart_IdAndProduct_Id(cartEntity.getId(), input.getProductId());
        Integer newNumber = entity.getNumber() + 1;

        /* Kiểm tra sản phẩm có đủ ko */
//        if(entity.getProduct().getNumber() < newNumber){
//            throw new RuntimeException("Sản phẩm không đủ");
//        }
        BigDecimal total = entity.getProduct().getPrice().multiply(BigDecimal.valueOf(newNumber));
        entity.setNumber(newNumber);
        entity.setTotal(total);
        repository.save(entity);

        /* Lưu thành tiền giỏ hàng */
        totalOfCart(entity.getCart().getId(), email);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<CartDetailDto> updateNumberDown(Integer customerId, String email ,CartDetailInput input) {
        CartEntity cartEntity = cartRepository.getByCustomer_Id(customerId, email);
        CartDetailEntity entity = repository.findByCart_IdAndProduct_Id(cartEntity.getId(), input.getProductId());
        Integer newNumber = entity.getNumber() - 1;

        /* Nếu sản phẩm <=0 thì xóa khỏi giỏ hàng */
        if(newNumber <= 0){
            CartDetalInputDelete newInput = new CartDetalInputDelete();
            newInput.setProductId(input.getProductId());
            delete(cartEntity.getId(), email,newInput);
            return ResponseEntity.ok().body(mapper.entityToDto(entity));
        }


        BigDecimal total = entity.getProduct().getPrice().multiply(BigDecimal.valueOf(newNumber));
        entity.setNumber(newNumber);
        entity.setTotal(total);
        repository.save(entity);

        totalOfCart(entity.getCart().getId(), email);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<CartDetailDto> updateNumber(Integer customerId, String email,CartDetailInput input) {

        /* Lấy thông tin sản phẩm */
        ProductEntity product = productRepository.getById(input.getProductId());

        /* Kiểm tra có đủ sản phẩm trong kho để thêm vào giỏ hàng hay ko */
//        if(product.getNumber() < input.getNumber()){
//            throw new RuntimeException("Sản phẩm"+ product.getName() +"trong kho không đủ");
//        }

//        /* Kiểm tra số lượng trong giỏ phải < 15 */
//        int checkNumOfCartDetail = repository.checkNumberOfCartDetail(input.getCartId());
//        if(checkNumOfCartDetail >=15){
//            throw new RuntimeException("Số lượng trong giỏ hàng không vượt quá 15 sản phẩm");
//        }

        /* Nếu số lượng <=0 thì xóa sản phẩm khỏi giỏ hàng */
        if(input.getNumber() <= 0){
            throw new RuntimeException("Số lượng sản phẩm phải lớn hơn 1");
        }

        /* Lấy thông tin Giỏ hàng */
        CartEntity cartEntity = cartRepository.getByCustomer_Id(customerId, email);

        CartDetailEntity entity = repository.findByCart_IdAndProduct_Id(cartEntity.getId(), input.getProductId());
        BigDecimal price = product.getPrice();
        mapper.inputToEntity(input, entity);
        entity.setTotal(price.multiply(BigDecimal.valueOf(input.getNumber())));
        repository.save(entity);

        totalOfCart(cartEntity.getId(), email);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<CartDetailDto> delete(Integer customerId, String email,CartDetalInputDelete input) {
        CartEntity cartEntity = cartRepository.getByCustomer_Id(customerId, email);
        CartDetailEntity entity = repository.findByCart_IdAndProduct_Id(cartEntity.getId(), input.getProductId());
        repository.delete(entity);
        cartEntity.setTotal(totalOfCart(cartEntity.getId(), email));
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public BigDecimal totalOfCart(Integer cartId, String email) {
        BigDecimal finalTotal = repository.totalOfCart(cartId);
        CartEntity cartEntity = cartRepository.getById(cartId);
        cartEntity.setTotal(finalTotal);
        return finalTotal;
    }

    @Override
    public Integer numberOfCartDetail(Integer cartId, String email) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<CartDetailDto> getOne(Integer cartDetailId) {
        CartDetailEntity entity = repository.getById(cartDetailId);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    public boolean checkNumberOfCart(Integer cartid, String email,Integer productId) {
        return false;
    }

    @Override
    @Transactional
    public Float getAllWeight(Integer cartId, String email) {
        Float sumWeight = repository.tinhTongCanNangCart(cartId);
        return sumWeight;
    }

    @Override
    public Integer getTotalItems(Integer cartid, String email) {
        Integer count = repository.totalItemsCart(cartid);
        return count;
    }

    @Override
    @Transactional
    public ResponseEntity<List<CartDetailDto>> createByCustomerNotLogin(Integer id, List<LocalStorageCartDetail> inputs) {
        for(LocalStorageCartDetail x : inputs){
            CartDetailInput input = new CartDetailInput();
            input.setNumber(x.getNumber());
            CartEntity billEntity = cartRepository.getById(id);

            CartDetailEntity entity = mapper.inputToEntity(input);

            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(x.getProduct_id());
            entity.setProduct(productEntity);

            entity.setCart(billEntity);
            entity.setTotal(x.getTotal());
            entity.setNumber(x.getNumber());
            repository.save(entity);

            String email ="";
            BigDecimal totalOfBill =  totalOfCart(billEntity.getId(), email);
            billEntity.setTotal(totalOfBill);
            cartRepository.save(billEntity);
        }
        List<CartDetailEntity> lst = new ArrayList<>();
        List<CartDetailDto> lstDto = mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(lstDto);
    }

    @Override
    @Transactional
    public int soluongtronggio(Integer idCutsomer, String email) {
        CartEntity entity = cartRepository.getByCustomer_IdAndEmail(idCutsomer, email)
                .orElseThrow(() -> new RuntimeException("Bạn chưa đăng ký tài khoản") );
        int count  = repository.coutofCart(entity.getId());
        return count;

    }

    @Override
    @Transactional
    public void check(String email) {
        CartEntity cartEntity = cartRepository.timCart(email);
        List<CartDetailEntity> lst = repository.findListByCartId(cartEntity.getId());
        for(CartDetailEntity x : lst){
            ProductEntity productEntity = productRepository.getById(x.getProduct().getId());
            if(productEntity.getNumber() < x.getNumber() ){
                throw new RuntimeException("Sản phẩm " + productEntity.getName() + " không đủ để mua");
            }

            if(productEntity.isStatus() == false){
                throw new RuntimeException("Sản phẩm " + productEntity.getName() + " hiện không khả dụng" );
            }
        }
        int count = repository.totalItemsCart(cartEntity.getId());
    }
}
