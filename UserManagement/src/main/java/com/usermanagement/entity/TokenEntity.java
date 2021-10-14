package com.usermanagement.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "table_token")
@Data
public class TokenEntity extends BaseEntity {
	@Column(length = 1000)
	private String token;
	private Date tokenExpDate;
}
