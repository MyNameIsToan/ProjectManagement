package com.usermanagement.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.config.JwtUtil;
import com.usermanagement.config.UserPrincipal;
import com.usermanagement.dto.GroupDTO;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.TokenEntity;
import com.usermanagement.service.IGroupService;
import com.usermanagement.service.ITokenService;

@RestController
@RequestMapping(value = "/api/v1/groups")
public class GroupController {
	@Autowired
	private ITokenService tokenService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private IGroupService groupService;

	@PostMapping()
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public void register(@RequestBody GroupDTO group) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			groupService.createGroup(group);
		}
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public void update(@PathVariable("id") Long groupid, @RequestBody GroupDTO model) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			groupService.updateGroup(groupid, model);
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public void delele(@PathVariable("id") Long groupid) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			groupService.deleteGroup(groupid);
		}
	}

	@GetMapping("/{GroupName}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public List<UserDTO> GetAllGroupOfUser(@PathVariable("GroupName") String GroupName) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			return groupService.GetAllUserOfGroup(GroupName);
		}else 
			return null;
	}
}
