package com.demo.duan.service.address;

import com.demo.duan.service.address.dto.AdressDto;
import com.demo.duan.service.address.input.AdressInput;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdressService {
    public ResponseEntity<List<AdressDto>> find(Integer customer_id, String email);

    public ResponseEntity<AdressDto> getOne(Integer id, String email);

    public ResponseEntity<AdressDto> create(AdressInput input, String email);

    public ResponseEntity<AdressDto> update(Integer id, String email , AdressInput input);

    public ResponseEntity<AdressDto> delete(Integer id, String email);

    public ResponseEntity<AdressDto> getMacdinh(Integer customerId, String email);
}
