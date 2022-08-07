package com.aamu.aamurest.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {
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
	private String socialnum;
	private List<String> theme;
}
