package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

}
