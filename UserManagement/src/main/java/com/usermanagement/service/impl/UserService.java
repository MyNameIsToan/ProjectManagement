package com.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.config.UserPrincipal;
import com.usermanagement.dto.GroupDTO;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.GroupEntity;
import com.usermanagement.entity.RoleEntity;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.repository.GroupRepository;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	//Register
	@Override
	public int createUser(UserDTO model) {
		UserEntity userentity1 = userRepository.findByUsername(model.getUsername());
		UserEntity userentity2 = userRepository.findByEmail(model.getEmail());
		if(userentity1 == null && userentity2 == null) {
			UserEntity user = new UserEntity();
			user.setUsername(model.getUsername());
			user.setPassword(model.getPassword());
			user.setEmail(model.getEmail());
			user.setBirthdate(model.getBirthdate());
			user.setLastname(model.getLastname());
			user.setFirstname(model.getLastname());
			RoleEntity role = roleRepository.findOne(1L);
			Set<RoleEntity> SetRole = new HashSet<RoleEntity>();
			SetRole.add(role);
			user.setRoles(SetRole);
			userRepository.save(user);
			return 1;
		}else {
			return 0;
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
			user.setBirthdate(model.getBirthdate());
			user.setLastname(model.getLastname());
			user.setFirstname(model.getLastname());
			user.setOrigin(model.getOrigin());
			user.setPhone(model.getPhone());
//			List<GroupEntity> ListGroup = new ArrayList<>();
//			for(String item: model.getGroup()) {
//				GroupEntity group = groupRepository.findByGroupName(item);
//				ListGroup.add(group);
//			}
//			user.setListGroup(ListGroup);
			userRepository.save(user);
		}
	}

	@Override
	public void addUser(String username, Long groupid) {
		UserEntity user = userRepository.findByUsername(username);
		GroupEntity group = groupRepository.findOne(groupid);
		if(user != null && group != null) {
			List<GroupEntity> ListGroup = user.getListGroup();
			ListGroup.add(group);
			user.setListGroup(ListGroup);
			userRepository.save(user);
		}	
	}

	@Override
	public void deleteUser(String username, Long groupid) {
		UserEntity user = userRepository.findByUsername(username);
		GroupEntity group = groupRepository.findOne(groupid);
		if(user != null && group != null) {
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
	}

	@Override
	public List<GroupDTO> GetAllGroupOfUser(String username) {
		UserEntity user = userRepository.findByUsername(username);
		if(user != null) {
			List<GroupDTO> group = new ArrayList<>();
			for(GroupEntity item : user.getListGroup())
			{
				GroupDTO SubGroup = new GroupDTO();
				SubGroup.setGroupName(item.getGroupName());
				SubGroup.setDescription(item.getGroupName());
				group.add(SubGroup);
			}
			return group;
		}else
			return null;
		
	}

	@Override
    public UserPrincipal findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (null != user) {
            Set<String> authorities = new HashSet<>();
            if (null != user.getRoles()) user.getRoles().forEach(r -> {
                authorities.add(r.getRoleKey());
                r.getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
            });

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setEnabled(user.isEnabled());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }

	@Override
	public void Verified(String Username) {
		UserEntity user = userRepository.findByUsername(Username);
		if(user == null) {
			return;
		}else {
			user.setEnabled(true);
			userRepository.save(user);
		}
	}
}
