package com.aamu.admin.main.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.CommuService;

@Service
public class CommuServiceImpl implements CommuService {
	
	@Autowired
	private CommuDAO dao;

}
