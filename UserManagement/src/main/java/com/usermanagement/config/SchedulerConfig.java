package com.usermanagement.config;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.usermanagement.entity.TokenEntity;
import com.usermanagement.service.ITokenService;

@Configuration
@EnableScheduling
public class SchedulerConfig {
	@Autowired
	private ITokenService tokenservice;
	@Scheduled( initialDelay = 0, fixedDelay = 180000)
    public void CleanToken() {
	   Date now = new Date();
       List<TokenEntity> token = tokenservice.FindAll();
       for(TokenEntity item : token) {
    	   if(item.getTokenExpDate().before(now)) {
    		   tokenservice.remove(item);
    		   System.out.println("Cleaned");
    	   }
       }
	}
}
