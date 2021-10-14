package com.usermanagement.service;

import com.usermanagement.dto.GroupDTO;

public interface IGroupService {
	void createGroup(GroupDTO group);
	void deleteGroup(Long groupid);
	void updateGroup(Long groupid, GroupDTO model);
}
