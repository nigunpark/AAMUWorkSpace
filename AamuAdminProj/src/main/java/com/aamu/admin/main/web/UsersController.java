package com.aamu.admin.main.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aamu.admin.main.serviceimpl.AdminCommuServiceImpl;
import com.aamu.admin.main.serviceimpl.UsersServicelmpl;

@Controller
public class UsersController {
	@Autowired
	private UsersServicelmpl usersService;
	
	//전체 회원 뿌려주기
	@RequestMapping("Users.do")
	public String usersSelectList() {
		
		//뷰정보 반환
		return "users/users";
	}
}
