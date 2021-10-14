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
import com.usermanagement.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GroupRepository groupRepository;
	
	//Register
	@Override
	public void createUser(UserDTO model) {
		UserEntity userentity = userRepository.findByUsername(model.getUsername());
		if(userentity == null) {
			UserEntity user = new UserEntity();
			user.setUsername(model.getUsername());
			user.setPassword(model.getPassword());
			user.setEmail(model.getEmail());
			user.setBirthdate(model.getBirthdate());
			user.setLastname(model.getLastname());
			user.setFirstname(model.getLastname());
			List<GroupEntity> ListGroup = new ArrayList<>();
			for(String item: model.getGroup()) {
				GroupEntity group = groupRepository.findByGroupName(item);
				ListGroup.add(group);
			}
			user.setListGroup(ListGroup);
			userRepository.save(user);
		}
	}
	
	//Delete
	@Override
	public void deleteUser(Long user_id) {
		UserEntity userEntity = userRepository.findOne(user_id);
		if(userEntity == null) {
			return;
		}else {
			userRepository.delete(userEntity);
		}
	}
	
	//Update
	@Override
	public void Update(Long user_id, UserDTO model) {
		UserEntity user = userRepository.findOne(user_id);
		if(user == null) {
			return;
		}else {
			user.setUsername(model.getUsername());
			user.setEmail(model.getEmail());
			user.setBirthdate(model.getBirthdate());
			user.setLastname(model.getLastname());
			user.setFirstname(model.getLastname());
			List<GroupEntity> ListGroup = new ArrayList<>();
			for(String item: model.getGroup()) {
				GroupEntity group = groupRepository.findByGroupName(item);
				ListGroup.add(group);
			}
			user.setListGroup(ListGroup);
			userRepository.save(user);
		}
	}

	@Override
	public void addUser(String username, Long groupid) {
		UserEntity user = userRepository.findByUsername(username);
		GroupEntity group = groupRepository.findOne(groupid);
		List<GroupEntity> ListGroup = user.getListGroup();
		ListGroup.add(group);
		user.setListGroup(ListGroup);
		userRepository.save(user);
	}

	@Override
	public void deleteUser(String username, Long groupid) {
		UserEntity user = userRepository.findByUsername(username);
		GroupEntity group = groupRepository.findOne(groupid);
		List<GroupEntity> ListGroup = user.getListGroup();
		int index = 0;
		for(GroupEntity SubGroup : ListGroup) {
			if(SubGroup.getId() == groupid) {
				index = ListGroup.indexOf(SubGroup);
			}
		}
		ListGroup.remove(index);
		user.setListGroup(ListGroup);
		userRepository.save(user);
	}

	@Override
	public List<GroupDTO> GetAllGroupOfUser(String username) {
		UserEntity user = userRepository.findByUsername(username);
		List<GroupDTO> group = new ArrayList<>();
		for(GroupEntity item : user.getListGroup())
		{
			GroupDTO SubGroup = new GroupDTO();
			SubGroup.setGroupName(item.getGroupName());
			SubGroup.setDescription(item.getGroupName());
			group.add(SubGroup);
		}
		return group;
	}	
}
