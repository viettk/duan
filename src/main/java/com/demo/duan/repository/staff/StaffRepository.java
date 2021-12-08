package com.demo.duan.repository.staff;

import com.demo.duan.entity.StaffEntity;
import com.demo.duan.service.staff.param.StaffParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Integer> {
    Optional<StaffEntity>findByEmail(String email);

    //tìm kiếm theo tên, email.
    @Query("select s from StaffEntity s where :#{#staff.email} is null or s.email like %:#{#staff.email}% " +
            "and :#{#staff.name} is null or s.name like %:#{#staff.name}% " +
            "and :#{#staff.status} is null or s.status=:#{#staff.status} " +
            "and :#{#staff.role} is null or s.role=:#{#staff.role}")
    Page<StaffEntity>filterByParam(@Param("staff")StaffParam staff, Pageable pageable);

    Optional<StaffEntity>findByPhone(String phone);

    Optional<StaffEntity> findByEmailAndStatusIsFalse(String email);
}
