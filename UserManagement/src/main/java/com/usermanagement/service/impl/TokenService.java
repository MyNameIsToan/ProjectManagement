package com.usermanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.TokenEntity;
import com.usermanagement.repository.TokenRepository;
import com.usermanagement.service.ITokenService;

@Service
public class TokenService implements ITokenService {
	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	public TokenEntity createToken(TokenEntity token) {
		 return tokenRepository.saveAndFlush(token);
	}

	 @Override
	    public TokenEntity findByToken(String token) {
	        return tokenRepository.findByToken(token);
	    }
	    
	    @Override
		public int remove(TokenEntity Token) {
			try{
				tokenRepository.delete(Token);
				return 0;
			}catch(Exception e) {
				return 1;
			}
		}

		@Override
		public List<TokenEntity> FindAll() {
			return tokenRepository.findAll();
		}
	
}
