package com.usermanagement.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="table_permission")
@Data
public class PermissionEntity extends BaseEntity {
	private String permissionName;
    private String permissionKey;
}
