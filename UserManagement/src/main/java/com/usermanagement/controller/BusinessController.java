package com.usermanagement.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.config.JwtUtil;
import com.usermanagement.config.UserPrincipal;
import com.usermanagement.entity.TokenEntity;
import com.usermanagement.service.ITokenService;
import com.usermanagement.service.IUserService;

@RestController
@RequestMapping(value="/api/v1/business/")
public class BusinessController {
	@Autowired
	private IUserService userService;

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest request;
	
	@PostMapping("/{groupcode}/{members}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public void AddToGroup(@PathVariable("groupcode") Long groupid, @PathVariable("members") String username) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			userService.addUser(username, groupid);
		}
	}

	@DeleteMapping("/{groupcode}/{members}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public void DeleteToGroup(@PathVariable("groupcode") Long groupid, @PathVariable("members") String username) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			userService.deleteUser(username, groupid);
		}
	}
}
