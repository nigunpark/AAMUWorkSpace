package com.aamu.admin.main.service;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private String id;
	private String email;
	private String pwd;
	private String name;
	private String gender;
	private String phonenum;
	private String addrid;
	private String self;
	private Date joindate;
	private String userprofile;
	
}
