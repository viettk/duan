package com.demo.duan.service.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UpLoadService{
    @Override
    public File savePhoto(MultipartFile file, String folder) {
        //System.getProperty("user.dir") để có được đường dẫn tuyệt đối của thư mục
        File dir = new File(System.getProperty("user.dir")+folder); // trỏ đến file
        // kiểm tra thư mục có tồn tại không
        if(!dir.exists()) {
            dir.mkdir();//nếu không thì tạo thư mục mới
        }
        String suffixes = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //kiểm tra file ảnh có hợp lệ
        if(suffixes.equals(".png") || suffixes.equals(".jpg") || suffixes.equals(".jpeg") || suffixes.equals(".gif")){
            String uuid = UUID.randomUUID().toString(); // sinh ra 1 mã duy nhất trên toàn project
            String nameOutput = uuid + suffixes;
            File savedFile = new File(dir,nameOutput);
            try {
                file.transferTo(savedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return savedFile;
        }else{
            throw new RuntimeException("Vui lòng chọn các loại file sau .png , .jpg  , .jpeg  , .gif");
        }
    }
}
