package com.usermanagement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private String phone;
	private String origin;
	private Date birthdate;
	private List<String> group = new ArrayList<>();
}
