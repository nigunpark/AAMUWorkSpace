package com.aamu.aamurest.user.serviceimpl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.admin.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeDAO dao;
	
}
