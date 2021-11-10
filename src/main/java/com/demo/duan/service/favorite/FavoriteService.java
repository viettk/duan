package com.demo.duan.service.favorite;

import com.demo.duan.service.favorite.dto.FavoriteDto;
import com.demo.duan.service.favorite.input.FavoriteInput;
import com.demo.duan.service.favorite.param.FavoriteParam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface FavoriteService {
    public ResponseEntity<Page<FavoriteDto>> find(Integer customerId ,FavoriteParam param, Optional<String> field, String known);

    public ResponseEntity<FavoriteDto> create(FavoriteInput input);

    public ResponseEntity<FavoriteDto> delete(FavoriteInput input);

    public int getProduct(Integer productId);
}
