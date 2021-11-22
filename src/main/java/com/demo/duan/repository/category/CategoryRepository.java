package com.demo.duan.repository.category;

import com.demo.duan.entity.CategoryEntity;
import com.demo.duan.service.category.dto.CategoryDto;
import com.demo.duan.service.category.param.CategoryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query("select count(c) from CategoryEntity c where c.name= :name and c.parent_name= :parent_name")
    long countCategory(@Param("name") String name, @Param("parent_name") String paren_name);

    @Query("from CategoryEntity c where (:#{#param.name} is null or c.name like %:#{#param.name}%)" +
            "and (:#{#param.parent_name} is null or c.parent_name like %:#{#param.parent_name}%)")
    Page<CategoryEntity> find(@Param("param")CategoryParam param, Pageable pageable);

    @Query("select distinct(c.parent_name) from CategoryEntity c")
    List<String> findParent();

    @Query("from CategoryEntity c where c.parent_name = 'Model Kit' ")
    List<CategoryEntity> getAllCategoryKit();

    @Query("from CategoryEntity c where c.parent_name = :parentName ")
    List<CategoryEntity> getAllCategoryByparent(String parentName);

    @Query("select distinct c from CategoryEntity c join ProductEntity  p on c.id = p.category.id  where (:#{#param.name} is null or c.name like %:#{#param.name}%) ")
    List<CategoryEntity> getAllCategoryByName(@Param("param")CategoryParam param);
}
