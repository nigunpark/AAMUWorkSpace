package com.aamu.aamurest.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.admin.service.DashBoardService;

@RestController
public class DashBoardController {
	
	@Autowired
	private DashBoardService service;
	
	@GetMapping("/admin/usertotalcount")
	public int userCount() {
		int count =0;
		
		count = service.userTotalCount();
		
		return count;
	}
	
}
