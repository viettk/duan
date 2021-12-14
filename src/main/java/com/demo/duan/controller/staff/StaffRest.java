package com.demo.duan.controller.staff;

import com.demo.duan.service.staff.StaffService;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.param.StaffParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/staff")
public class StaffRest {

    private final StaffService service;

    @GetMapping("/email")
    public ResponseEntity<StaffDto>getByEmail(@RequestParam("email") String email){
        return this.service.getByUsername(email);
    }

    @GetMapping
    public ResponseEntity<Page<StaffDto>>search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) Boolean status,
            @RequestParam(value = "role", required = false) Integer role,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        StaffParam param = new StaffParam(email, name, status, role);
        if (known.isEmpty()){
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            return service.searchByParam(param, pageable);
        }else {
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            return service.searchByParam(param, pageable);
        }
    }

    @PostMapping
    public ResponseEntity<StaffDto>create(@RequestBody StaffInput staffInput){
        return this.service.createStaff(staffInput);
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<StaffDto>update(@PathVariable("id") Integer id, @RequestBody StaffInput staffInput){
        return this.service.resetPassord(id, staffInput);
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
