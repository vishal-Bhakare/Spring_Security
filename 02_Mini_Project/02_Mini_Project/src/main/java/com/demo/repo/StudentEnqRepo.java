package com.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

	
}
