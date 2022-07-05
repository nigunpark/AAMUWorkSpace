package com.aamu.aamurest.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aamu.aamurest.user.service.BBSService;

@CrossOrigin(origins="")

@RestControllerAdvice
@RestController
public class BBSController {
	
	@Autowired
	private BBSService bbsservice;
	
	
	
	
}
