package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.TokenEntity;

public interface ITokenService {
	TokenEntity createToken(TokenEntity token);
	TokenEntity findByToken(String token);
	int remove(TokenEntity Token);
	List<TokenEntity> FindAll();
}
