package com.usermanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="table_group")
@Data
public class GroupEntity extends BaseEntity{
	private String groupName;
	@Column(length = 1000)
	private String description;
	@ManyToMany(mappedBy = "listGroup")
    private List<UserEntity> listUser = new ArrayList<>();
}
