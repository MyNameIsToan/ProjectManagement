package com.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.dto.GroupDTO;
import com.usermanagement.service.IGroupService;

@RestController
public class GroupController {
	
	@Autowired
	private IGroupService groupService;

	@PostMapping("/Group/create")
	public void register(@RequestBody GroupDTO group) {
		groupService.createGroup(group);
	}
	
	@PutMapping("/Group/update/{id}")
	public void update(@PathVariable("id") Long groupid ,@RequestBody GroupDTO model) {
		groupService.updateGroup(groupid, model);
	}
	
	@DeleteMapping("/Group/delete/{id}")
	public void delele(@PathVariable("id") Long groupid) {
		groupService.deleteGroup(groupid);
	}
}
