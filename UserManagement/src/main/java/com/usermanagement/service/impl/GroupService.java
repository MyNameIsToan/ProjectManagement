package com.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.dto.GroupDTO;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.GroupEntity;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.repository.GroupRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.IGroupService;

@Service
public class GroupService implements IGroupService {
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//Create new Group
	@Override
	public void createGroup(GroupDTO group) {
		GroupEntity groupentity = new GroupEntity();
		groupentity.setGroupName(group.getGroupName());
		groupentity.setDescription(group.getDescription());
		List<UserEntity> ListUser = new ArrayList<>();
		for(String item : group.getUser()) {
			UserEntity user = userRepository.findByUsername(item);
			ListUser.add(user);
		}
		groupentity.setListUser(ListUser);
		groupRepository.save(groupentity);
	}
	
	//Delete Group
	@Override
	public void deleteGroup(Long groupid) {
		GroupEntity group = groupRepository.findOne(groupid);
		if(group == null) {
			return;
		}else {
			groupRepository.delete(group);
		}
	}
	
	//Update Group
	@Override
	public void updateGroup(Long groupid, GroupDTO model) {
		GroupEntity groupentity = groupRepository.findOne(groupid);
		if(groupentity != null)
		{
			groupentity.setGroupName(model.getGroupName());
			groupentity.setDescription(model.getDescription());
			List<UserEntity> ListUser = new ArrayList<>();
			for(String item : model.getUser()) {
				UserEntity user = userRepository.findByUsername(item);
				ListUser.add(user);
			}
			groupentity.setListUser(ListUser);
			groupRepository.save(groupentity);
		}
	}

	@Override
	public List<UserDTO> GetAllUserOfGroup(String GroupName) {
		GroupEntity group = groupRepository.findByGroupName(GroupName);
		if(group == null)
		{
			List<UserDTO> user = new ArrayList<>();
			for(UserEntity item : group.getListUser())
			{
				UserDTO SubUser = new UserDTO();
				SubUser.setUsername(item.getUsername());
				user.add(SubUser);
			}
			return user;
		}else
			return null;
	}
}
