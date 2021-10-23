package com.demo.duan.controller.staff;

import com.demo.duan.service.staff.StaffService;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/staff")
public class StaffRest {

    private final StaffService service;

    @GetMapping("/get")
    public ResponseEntity<Page<StaffDto>>getAll(
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getStaff(limit,page,field,known);
    }

    @PostMapping("/create")
    public ResponseEntity<StaffDto>createStaff(@RequestBody StaffInput staffInput){
        return this.service.createStaff(staffInput);
    }

    @PutMapping("/ipdate")
    public ResponseEntity<StaffDto>updateStaff(@RequestBody StaffInput staffInput, @RequestParam("id") Integer id){
        return  this.service.updateStaff(id, staffInput);
    }
}
