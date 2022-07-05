package com.aamu.aamurest.user.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.BBSService;

@Service
public class BBSServiceImpl implements BBSService{
	
	@Autowired
	private BBSDao dao;
}
