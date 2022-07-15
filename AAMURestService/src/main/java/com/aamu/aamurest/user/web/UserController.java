package com.aamu.aamurest.user.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.user.service.UsersService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UsersService service;
	
	
	@PostMapping("/users/insert")
	public int join(@RequestBody UsersDTO dto) {
		int affected=0;
		
		affected = service.joinUser(dto);
		
		return affected;
	}
	
	@GetMapping("/users/selectone")
	public UsersDTO selectOneUser(@RequestParam Map map) {
		
		UsersDTO dto = service.selectOneUser(map);
		
		return dto;
	}
	
	
	@PutMapping("/users/update")
	public int updateUser(@RequestBody UsersDTO dto) {
		int affected=0;
		
		affected = service.updateUser(dto);
		
		return affected;
	}
	@GetMapping("/users/checkid")
	public int checkId(@RequestParam String id) {
		System.out.println(id);
		return service.checkId(id);
	}
	
	
}
