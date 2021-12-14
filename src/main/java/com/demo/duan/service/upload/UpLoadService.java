package com.demo.duan.service.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UpLoadService {
    public File savePhoto(MultipartFile file, String folder);
}
