package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long>{
	TokenEntity findByToken(String token);
}
