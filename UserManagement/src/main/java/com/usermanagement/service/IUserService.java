package com.usermanagement.service;

import java.util.List;

import com.usermanagement.config.UserPrincipal;
import com.usermanagement.dto.GroupDTO;
import com.usermanagement.dto.UserDTO;

public interface IUserService {
	int createUser(UserDTO model);
	void deleteUser(Long user_id);
	void Update(Long user_id, UserDTO model);
	void addUser(String username, Long groupid);
	void deleteUser(String username, Long groupid);
	List<GroupDTO> GetAllGroupOfUser(String username);
	UserPrincipal findByUsername(String username);
	void Verified(String Username);
}
