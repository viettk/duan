package com.demo.duan.service.photo;

import com.demo.duan.service.upload.UpLoadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService{
    private final UpLoadService upLoadService;
    @Override
    public ResponseEntity<Void> upLoad(MultipartFile file) {
        return null;
    }
}
