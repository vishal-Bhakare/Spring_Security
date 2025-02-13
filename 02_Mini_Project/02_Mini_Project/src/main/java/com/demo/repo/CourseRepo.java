package com.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
