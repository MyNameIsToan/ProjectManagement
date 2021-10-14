package com.usermanagement.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GroupDTO {
	private String GroupName;
	private String Description;
	private List<String> user = new ArrayList<>();
}
