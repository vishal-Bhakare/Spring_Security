package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cjc.entity.UserDtlEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlEntity, Integer> {

	public UserDtlEntity findByEmail(String email);

	public UserDtlEntity findByEmailAndPwd(String email, String pwd);
}
