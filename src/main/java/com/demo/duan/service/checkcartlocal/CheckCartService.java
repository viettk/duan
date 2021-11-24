package com.demo.duan.service.checkcartlocal;

import com.demo.duan.entity.LocalStorageBillDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CheckCartService {
    public ResponseEntity<List<LocalStorageBillDetail>> checkCart(List<LocalStorageBillDetail> inputs);
}
