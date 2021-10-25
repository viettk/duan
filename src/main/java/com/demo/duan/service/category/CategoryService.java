package com.demo.duan.service.category;

import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.input.CategoryInput;
import com.demo.duan.service.category.param.CategoryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    public ResponseEntity<Page<CategoryDto>> find(CategoryParam param, Pageable pageable);

    public ResponseEntity<CategoryDto> create(CategoryInput input);

    public ResponseEntity<CategoryDto> update(Integer id ,CategoryInput input);
}
