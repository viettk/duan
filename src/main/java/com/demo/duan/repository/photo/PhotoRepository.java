package com.demo.duan.repository.photo;

import com.demo.duan.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {
    List<PhotoEntity> findAllByProduct_Id(Integer id);
}
