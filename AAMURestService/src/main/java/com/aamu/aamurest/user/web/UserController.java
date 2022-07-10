package com.aamu.aamurest.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.user.service.UsersService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UsersService service;
	
	
	@PutMapping("users/join")
	public int join(@RequestBody UsersDTO dto) {
		int affected=0;
		
		affected = service.joinUser(dto);
		
		return affected;
	}
	
}
