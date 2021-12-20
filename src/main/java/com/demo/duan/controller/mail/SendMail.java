package com.demo.duan.controller.mail;

import com.demo.duan.service.customer.dto.CustomerDto;
import com.demo.duan.service.mail.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api")
@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class SendMail {
    private final MailService mailService;
    @PostMapping("/sendmail")
    public ResponseEntity<Void> getEmail(@PathParam("title") String title,@PathParam("content") String content
            ,@PathParam("file") Optional<MultipartFile> file,@PathParam("ids") Integer[] ids) throws MessagingException {
        Map<String, String> errors = new HashMap<>();
        if(title.isEmpty()){
            errors.put("title","Không được để trống tiêu đề");
        }
        if(content.isEmpty()){
            errors.put("content","Không được để trống nội dung");
        }
        if(!errors.isEmpty()){
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        mailService.sendAll(file.orElse(null),title,content,ids);
        return ResponseEntity.ok().build();
    }
}
