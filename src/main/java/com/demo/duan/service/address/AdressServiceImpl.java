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
    public ResponseEntity<List<AdressDto>> find() {
        List<AdressEntity> lst = repository.findAll();
        List<AdressDto> dto = mapper.EntitiesToDtos(lst);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<AdressDto> create(AdressInput input) {

        /* KHông cần thiết - Kiểm tra tài khoản đã có hay chưa */
        CustomerEntity customerEntity =customerRepository.findById(input.getCustomerInput())
                .orElseThrow(()-> new RuntimeException("Bạn chưa có tài khoản"));

        if(input.getCustomerInput().equals("") ){
            throw new RuntimeException("Bạn chưa có tài khoản");
        }

        /* Nếu số lượng địa chỉ > 5 thì ko đc thêm địa chỉ nữa */
        long num = repository.countAllByCustomer_Id(input.getCustomerInput());
        if(num >= 3){
            throw new RuntimeException("Chỉ có tối đa 3 địa chỉ");
        }

        /* Kiểm tra tài khoản đã tồn tại địa chỉ hay chưa */
       long count = repository.countAllByAddressAndCityAndDistrictAndCustomer_Id(
                input.getAddress(), input.getCity(), input.getDistrict(),input.getCustomerInput()
        );

       if(count>0){
           throw new RuntimeException("Địa chỉ đã tồn tại");
       }

       /* Lưu địa chỉ vào db */
        AdressEntity entity = mapper.inputToEntity(input);
        entity.setCustomer(customerEntity);
        /* Kiểm tra nếu tạo địa chỉ mới ở chế độ mặc đinh thì set các địa chỉ khách về ko mặc định */
        if(input.getStatus().booleanValue()==false){
            List<AdressEntity> lst = repository.findAllByCustomer_Id(input.getCustomerInput());
            for(AdressEntity x : lst){
                x.setStatus(true);
            }
        }
        repository.save(entity);

        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<AdressDto> update(Integer id, AdressInput input) {

        /* Kiểm tra địa chỉ có bị trùng với địa chỉ khác hay ko */
        long count = repository.countAllByAddressAndCityAndDistrictAndCustomer_Id(
                input.getAddress(), input.getCity(), input.getDistrict(),input.getCustomerInput()
        );

        if(count>1){
            throw new RuntimeException("Địa chỉ đã tồn tại");
        }

        /* Kiểm tra nếu tạo địa chỉ mới ở chế độ mặc đinh thì set các địa chỉ khách về ko mặc định */
        if(input.getStatus().booleanValue()==false){
            List<AdressEntity> lst = repository.findAllByCustomer_Id(input.getCustomerInput());
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
    public ResponseEntity<AdressDto> delete(Integer id) {
        AdressEntity entity = repository.getById(id);
        repository.delete(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }
}
