package com.demo.duan.controller.staff;

import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.staff.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("test")
@AllArgsConstructor
public class demo {

    private final StaffRepository repository;

    @GetMapping
    public List<StaffEntity> demo(@RequestParam("name") String name){
        return repository.searchName(name);
    }
}
