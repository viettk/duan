package com.demo.duan.service.category;

import com.demo.duan.entity.CategoryEntity;
import com.demo.duan.repository.category.CategoryRepository;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import com.demo.duan.service.category.mapper.CategoryMapper;
import com.demo.duan.service.category.param.CategoryParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryMapper mapper;

    private final CategoryRepository repository;

    @Override
    @Transactional
    public ResponseEntity<Page<CategoryDto>> find(
            CategoryParam param,
            Optional<Integer> limit,
            Optional<Integer> page ) {
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(5), Sort.by(Sort.Direction.DESC, "id"));
        Page<CategoryDto> entity = repository.find(param, pageable).map(mapper::entityToDto);
        return ResponseEntity.ok().body(entity);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryDto> create(CategoryInput input) {

        /* Kiểm tra đã tồn tại danh mục hay chưa */
        long count = repository.countCategory(input.getName(), input.getParent_name());

        if(count > 0){
            System.out.println("lỗi");
            throw new RuntimeException("Danh mục đã tồn tại");
        }

        CategoryEntity entity = mapper.inputToEntity(input);
        repository.save(entity);
        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryDto> update(Integer id ,CategoryInput input) {

        CategoryEntity entity = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Danh mục không tồn tại"));

        /* Kiểm tra đã tồn tại danh mục hay chưa */
        long count = repository.countCategory(input.getName(), input.getParent_name());

        if(count > 0){
            throw new RuntimeException("Danh mục đã tồn tại");
        }

        mapper.inputToEntity(input, entity);
        repository.save(entity);

        return ResponseEntity.ok().body(mapper.entityToDto(entity));
    }

    @Override
    @Transactional
    public ResponseEntity<List<String>> findParent() {
        List<String> lstEntities = repository.findParent();
        return ResponseEntity.ok().body(lstEntities);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryDto> get(Integer id) {
        CategoryEntity entity = repository.getById(id);
        CategoryDto dto = mapper.entityToDto(entity);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryEntity> entities = repository.findAll();
        List<CategoryDto> dtos = mapper.EntitiesToDtos(entities);
        return ResponseEntity.ok().body(dtos);
    }

    @Override
    @Transactional
    public ResponseEntity<List<CategoryDto>> getKit() {
        List<CategoryEntity> entities = repository.getAllCategoryKit();
        List<CategoryDto> dtos = mapper.EntitiesToDtos(entities);
        return ResponseEntity.ok().body(dtos);
    }

    @Override
    @Transactional
    public ResponseEntity<List<CategoryDto>> getCatebyParen(String parentname) {
        List<CategoryEntity> lstenEntities = repository.getAllCategoryByparent(parentname);
        List<CategoryDto> dtos = mapper.EntitiesToDtos(lstenEntities);
        return ResponseEntity.ok().body(dtos);
    }
}
