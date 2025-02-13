package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjc.entity.EnquiryStatusEntity;

@Repository
public interface EnquiryStatusRepo extends JpaRepository<EnquiryStatusEntity, Integer>{

}
