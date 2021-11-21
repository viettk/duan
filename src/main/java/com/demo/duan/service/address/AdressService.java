package com.demo.duan.service.address;

import com.demo.duan.service.address.dto.AdressDto;
import com.demo.duan.service.address.input.AdressInput;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdressService {
    public ResponseEntity<List<AdressDto>> find(Integer customer_id);

    public ResponseEntity<AdressDto> getOne(Integer customer_id);

    public ResponseEntity<AdressDto> create(AdressInput input);

    public ResponseEntity<AdressDto> update(Integer id , AdressInput input);

    public ResponseEntity<AdressDto> delete(Integer id);

    public ResponseEntity<AdressDto> getMacdinh(Integer customerId);
}
