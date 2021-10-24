package com.demo.duan.repository.category;

import com.demo.duan.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query("select count(c) from CategoryEntity c where c.name= :name and c.parent_name= :parent_name")
    long countCategory(@Param("name") String name, @Param("parent_name") String paren_name);
}
