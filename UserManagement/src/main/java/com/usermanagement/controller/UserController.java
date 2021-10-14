package com.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.service.IUserService;

@RestController
public class UserController {
	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public void register(@RequestBody UserDTO model) {
		model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		userService.createUser(model);
	}
	
	@PutMapping("/update/{id}")
	public void update(@PathVariable("id") Long userid ,@RequestBody UserDTO model) {
		userService.Update(userid, model);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delele(@PathVariable("id") Long userid) {
		userService.deleteUser(userid);
	}
	
	@PostMapping("/add/{id}/{username}")
	public void AddToGroup(@PathVariable("id") Long groupid, @PathVariable("username") String username) {
		userService.addUser(username, groupid);
	}
	
	@PostMapping("/delete/{id}/{username}")
	public void DeleteToGroup(@PathVariable("id") Long groupid, @PathVariable("username") String username) {
		userService.deleteUser(username, groupid);
	}
}
