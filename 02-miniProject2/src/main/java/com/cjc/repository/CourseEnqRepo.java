package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjc.entity.CourseEntity;

@Repository
public interface CourseEnqRepo extends JpaRepository<CourseEntity, Integer> {

}
