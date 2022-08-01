package com.aamu.aamurest.user.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.admin.NoticeService;

@RestController
@CrossOrigin
public class NoticeController {
	@Autowired
	private NoticeService service;
	
	
}
