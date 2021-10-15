package com.usermanagement.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.usermanagement.dto.TokenDTO;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.TokenEntity;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.service.ITokenService;
import com.usermanagement.service.IUserService;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
	@Autowired
	private IUserService userService;

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	public JavaMailSender emailSender;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserEntity user) {
		UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());
		if (null == user || userPrincipal.isEnabled() == false
				|| !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
		}
		TokenEntity token = new TokenEntity();
		token.setToken(jwtUtil.generateToken(userPrincipal));
		token.setTokenExpDate(jwtUtil.generateExpirationDate());
		token.setCreatedBy(userPrincipal.getUserId());
		tokenService.createToken(token);
		TokenDTO tokenDTO = new TokenDTO();
		return ResponseEntity.ok(token.getToken());
	}

	@PostMapping("/register")
	public void register(@RequestBody UserDTO model) {
		model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		int condition = userService.createUser(model);
		if (condition == 1) {
			String URL = "http://localhost:8083/api/v1/users/verify/" + model.getUsername();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(model.getEmail());
			message.setSubject("Confirm your Account");
			message.setText("Click here to verify: " + URL);
			this.emailSender.send(message);
		} else {
			return;
		}
	}

	@GetMapping("/signout")
	public ResponseEntity<String> Logout() {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			if (tokenService.remove(token) == 0) {
				return ResponseEntity.ok("Logout");
			} else {
				return ResponseEntity.ok("False");
			}
		}
		return ResponseEntity.ok("False");
	}

	@GetMapping("/verify/{Username}")
	public void Verify(@PathVariable("Username") String Username) {
		userService.Verified(Username);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public void update(@PathVariable("id") Long userid, @RequestBody UserDTO model) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			userService.Update(userid, model);
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public void delele(@PathVariable("id") Long userid) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			userService.deleteUser(userid);
		}
	}

	@GetMapping("/{username}")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public List<GroupDTO> GetAllGroupOfUser(@PathVariable("username") String username) {
		final String authorizationHeader = request.getHeader("Authorization");
		UserPrincipal user = null;
		TokenEntity token = null;
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			user = jwtUtil.getUserFromToken(jwt);
			token = tokenService.findByToken(jwt);
		}
		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			return userService.GetAllGroupOfUser(username);
		} else
			return null;
	}
}
