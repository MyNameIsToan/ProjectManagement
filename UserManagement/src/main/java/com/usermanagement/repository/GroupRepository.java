package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>{
	GroupEntity findByGroupName(String username);
}
