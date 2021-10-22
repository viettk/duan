package com.demo.duan.service.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UpLoadService{
    @Override
    public File savePhoto(MultipartFile file, String folder) {
        File dir = new File(System.getProperty("user.dir")+folder);
        // kiểm tra thư mục file có tồn tại không
        if(!dir.exists()) {
            dir.mkdir();//nếu không thì tạo file mới
        }
        String suffixes = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if(!suffixes.equals(".png") || !suffixes.equals(".jpg") || !suffixes.equals(".jpeg") || !suffixes.equals(".gif")){
            throw new RuntimeException("Vui lòng chọn các loại file sau .png , .jpg  , .jpeg  , .gif");
        }
        String uuid = UUID.randomUUID().toString(); // sinh ra 1 mã duy nhất trên toàn project
        String nameOutput = uuid + suffixes;
        try {
            //Lưu file vào thư mục đã chọn
            File savedFile = new File(dir,nameOutput);
            file.transferTo(savedFile);
            return savedFile;
        }catch (Exception e) {
            throw new RuntimeException("e");
        }
    }
}
