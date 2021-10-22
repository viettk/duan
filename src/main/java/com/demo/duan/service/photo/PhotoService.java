package com.demo.duan.service.photo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    ResponseEntity<Void> upLoad(MultipartFile file);
}
