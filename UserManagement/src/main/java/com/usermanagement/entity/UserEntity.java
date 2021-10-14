package com.usermanagement.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Table(name="table_user")
@Data
public class UserEntity extends BaseEntity {
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String origin;
	private String phone;
	private String email;
	private Date birthdate;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "group_user", 
				joinColumns = {@JoinColumn(name = "user_id")}, 
				inverseJoinColumns = {@JoinColumn(name = "group_id")})
	private List<GroupEntity> listGroup = new ArrayList<>();
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
				joinColumns =  {@JoinColumn(name = "user_id")} , 
				inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<RoleEntity> roles = new HashSet<>();
}
