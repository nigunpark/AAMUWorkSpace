package com.aamu.aamurest.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamu.aamurest.user.serviceimpl.MyPageImpl;

@RestController
@CrossOrigin(origins="*")
public class MyPageController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MyPageImpl myPageService;

}
