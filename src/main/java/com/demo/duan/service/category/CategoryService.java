package com.demo.duan.service.category;

import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import com.demo.duan.service.category.param.CategoryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public ResponseEntity<Page<CategoryDto>> find(CategoryParam param, Optional<String> field, Optional<String> known, Optional<Integer> limit, Optional<Integer> page );

    public ResponseEntity<CategoryDto> create(CategoryInput input);

    public ResponseEntity<CategoryDto> update(Integer id ,CategoryInput input);

    public ResponseEntity<List<String>> findParent();

    public ResponseEntity<CategoryDto> get(Integer id);

    public ResponseEntity<List<CategoryDto>> getAll();

    public ResponseEntity<List<CategoryDto>> getKit();

    public ResponseEntity<List<CategoryDto>> getCatebyParen(String parentname);

    public ResponseEntity<List<CategoryDto>> getAllSearch(CategoryParam params);

    public ResponseEntity<List<CategoryDto>> getAllCate(String name);
}
