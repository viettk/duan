package com.demo.duan.controller.bill;

import com.demo.duan.entity.BillReturnDetailEntity;
import com.demo.duan.entity.BillReturnEntity;
import com.demo.duan.service.billreturn.BillReturnService;
import com.demo.duan.service.billreturn.dto.BillReturnDetailDto;
import com.demo.duan.service.billreturn.dto.BillReturnDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/bill-return")
public class BillReturn {

    private final BillReturnService service;

    @GetMapping
    public ResponseEntity<Page<BillReturnDto>> getAll(
            @RequestParam(value = "_limit", required = false) Optional<Integer> limit,
            @RequestParam(value = "_page", required = false) Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        if (known.isEmpty()){
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            return service.findAll(pageable);
        }else {
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            return service.findAll(pageable);
        }
    }
    @GetMapping("/status")
    public ResponseEntity<Page<BillReturnDto>> getByStatus(
            @RequestParam(value = "status") String status,
            @RequestParam(value = "_limit", required = false) Optional<Integer> limit,
            @RequestParam(value = "_page", required = false) Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        if (known.isEmpty()){
            Sort sort = Sort.by(Sort.Direction.DESC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            return service.findAll(status, pageable);
        }else {
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("create_date"));
            Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(1), sort);
            return service.findAll(status, pageable);
        }
    }

    @PostMapping
    public ResponseEntity<BillReturnDto>create(@RequestBody BillReturnEntity entity){
        return ResponseEntity.ok().body(service.returnBill(entity));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillReturnDto>update(@PathVariable("id") Integer id, @RequestBody BillReturnEntity entity){
        return ResponseEntity.ok().body(service.updateBillReturn(id, entity));
    }
    @PostMapping("/{id}")
    public ResponseEntity<BillReturnDto>confirm(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(service.confirmBillReturn(id));
    }
    @PostMapping("/undo/{id}")
    public ResponseEntity<BillReturnDto>undo(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(service.undoBillReturn(id));
    }
    @GetMapping("/total/{id}")
    public ResponseEntity<BillReturnDto>total(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(service.totalBillReturn(id));
    }
    // chi tiet

    @GetMapping("/detail/{id}")
    public ResponseEntity<List<BillReturnDetailDto>>getdetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(service.getDetail(id));
    }

    @PostMapping("/detail")
    public ResponseEntity<BillReturnDetailDto> createDetail(@RequestBody BillReturnDetailEntity entity){
        return ResponseEntity.ok().body(service.returnDetail(entity));
    }
    @PostMapping("/detail-cf{id}")
    public ResponseEntity<BillReturnDetailDto> createDetailConfirm(@PathVariable("id")Integer id, @RequestBody BillReturnDetailEntity entity){
        return ResponseEntity.ok().body(service.returnDetailConfirm(id, entity));
    }

}
