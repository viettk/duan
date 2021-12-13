package com.demo.duan.service.upload;

import org.springframework.util.ResourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UpLoadService{
    @Override
    public File savePhoto(MultipartFile file, String folder) {
//        String path = System.getProperty("user.dir"); // trỏ đến file
//        path = path.substring(0,path.lastIndexOf("duan")) + folder;
//        //System.getProperty("user.dir") để có được đường dẫn tuyệt đối của thư mục
//        File dir = new File(path); // trỏ đến file
//        // kiểm tra thư mục có tồn tại không
//        if(!dir.exists()) {
//            dir.mkdir();//nếu không thì tạo thư mục mới
//        }
//        //kiểm tra file ảnh có hợp lệ
//        String s = System.currentTimeMillis() + file.getOriginalFilename();
//        String nameOutput = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
//        File savedFile = new File(dir,nameOutput);
//        try {
//            file.transferTo(savedFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return savedFile;
        try {
            String s = System.currentTimeMillis() + file.getOriginalFilename();
            String nameOutput = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));

            var path = ResourceUtils.getURL("classpath:static/images/");
            File savedFile = new File(path.getPath(),nameOutput);

            file.transferTo(savedFile);
            return savedFile;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
