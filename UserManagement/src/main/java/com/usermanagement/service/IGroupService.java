package com.usermanagement.service;

import java.util.List;

import com.usermanagement.dto.GroupDTO;
import com.usermanagement.dto.UserDTO;

public interface IGroupService {
	void createGroup(GroupDTO group);
	void deleteGroup(Long groupid);
	void updateGroup(Long groupid, GroupDTO model);
	List<UserDTO> GetAllUserOfGroup(String GroupName);
}
