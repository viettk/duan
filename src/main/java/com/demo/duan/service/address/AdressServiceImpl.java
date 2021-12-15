package com.demo.duan.service.address;

import com.demo.duan.entity.AdressEntity;
import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.repository.adress.AdressRepository;
import com.demo.duan.repository.customer.CustomerRepository;
import com.demo.duan.service.address.dto.AdressDto;
import com.demo.duan.service.address.input.AdressInput;
import com.demo.duan.service.address.mapper.AdressMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AdressServiceImpl implements AdressService {

    private final AdressRepository repository;

    private final AdressMapper mapper;

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public ResponseEntity<List<AdressDto>> find(Integer customer_id, String email) {
        List<AdressEntity> lst = repository.findAllList(customer_id, email);
        List<AdressDto> dto = mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<AdressDto> getOne(Integer id, String email) {
        AdressEntity entity = repository.getOne(id, email);
        AdressDto dto = mapper.entityToDto(entity);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<AdressDto> create(AdressInput input, String email) {

        /* KHông cần thiết - Kiểm tra tài khoản đã có hay chưa */
        CustomerEntity customerEntity =customerRepository.findByIdAndEmail(input.getCustomerInput(), email)
                .orElseThrow(()-> new RuntimeException("Bạn chưa có tài khoản"));
        System.out.println(input.getCustomerInput());
        if(input.getCustomerInput().equals("") ){
            throw new RuntimeException("Bạn chưa có tài khoản");
        }

        /* Nếu số lượng địa chỉ > 5 thì ko đc thêm địa chỉ nữa */
        long num = repository.countAllByCustomer_IdAndCustomer_Email(input.getCustomerInput(), email);
        if(num > 3){
            throw new RuntimeException("Chỉ có tối đa 3 địa chỉ");
        }

        /* Kiểm tra tài khoản đã tồn tại địa chỉ hay chưa */
       long count = repository.countAllByAddressAndCityAndDistrictAndCustomer_Id(
                input.getAddress(), input.getCity(), input.getDistrict(),input.getCustomerInput()
        );

       if(count>0){
           throw new RuntimeException("Địa chỉ đã tồn tại");
       }
        /* Kiểm tra nếu tạo địa chỉ mới ở chế độ mặc đinh thì set các địa chỉ khách về ko mặc định */
        if(input.getStatus().booleanValue()==false){
            List<AdressEntity> lst = repository.findAllList(input.getCustomerInput(), email);
            for(AdressEntity x : lst){
                x.setStatus(true);
            }
        }

        /* Lưu địa chỉ vào db */
        AdressEntity entity = mapper.inputToEntity(input);
        entity.setCustomer(customerEntity);
        repository.save(entity);

        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<AdressDto> update(Integer id, String email ,AdressInput input) {

        /* Kiểm tra địa chỉ có bị trùng với địa chỉ khác hay ko */
        long count = repository.countAllByAddressAndCityAndDistrictAndCustomer_Id(
                input.getAddress(), input.getCity(), input.getDistrict(),input.getCustomerInput()
        );

        if(count>1){
            throw new RuntimeException("Địa chỉ đã tồn tại");
        }

        /* Kiểm tra nếu tạo địa chỉ mới ở chế độ mặc đinh thì set các địa chỉ khách về ko mặc định */

        if(input.getStatus().booleanValue() == false){
            System.out.println(input.getCustomerInput() + "ok");
            List<AdressEntity> lst = repository.findAllList(input.getCustomerInput(), email);
            System.out.println(input.getCustomerInput());
            for(AdressEntity x : lst){
                x.setStatus(true);
                repository.save(x);
            }

        }

        AdressEntity entity = repository.getById(id);
        mapper.inputToEntity(input, entity);

        repository.save(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<AdressDto> delete(Integer id, String email) {
        AdressEntity entity = repository.getById(id);
        repository.delete(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    public ResponseEntity<AdressDto> getMacdinh(Integer customerId, String email) {
        AdressEntity entity = repository.findStatus(customerId, email);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }
}
