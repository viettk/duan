package com.demo.duan.controller.staff;

import com.demo.duan.service.staff.StaffService;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.param.StaffParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/staff")
public class StaffRest {

    private final StaffService service;

    @GetMapping
    public ResponseEntity<Page<StaffDto>>getAll(
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getStaff(limit,page,field,known);
    }

    @GetMapping("/email")
    public ResponseEntity<StaffDto>getByEmail(@RequestParam("email") String email){
        return this.service.getByUsername(email);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StaffDto>>search(
            @RequestBody StaffParam param,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.searchByParam(param, limit, page, field, known);
    }

    @PostMapping
    public Object createStaff(@RequestBody StaffInput staffInput){
        return this.service.createStaff(staffInput);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDto>updateStaff(@RequestBody StaffInput staffInput, @PathVariable("id") Integer id){
        return  this.service.updateStaff(id, staffInput);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<StaffDto>disableStaff(@PathVariable("id") Integer id){
        return this.service.disableStaff(id);
    }
}
