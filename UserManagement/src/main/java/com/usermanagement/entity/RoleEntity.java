package com.usermanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Table(name="table_role")
@Data
public class RoleEntity extends BaseEntity {
	private String roleName;
    private String roleKey;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", 
    			joinColumns = {@JoinColumn(name = "role_id")}, 
    			inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<PermissionEntity> permissions = new HashSet<>();
}
