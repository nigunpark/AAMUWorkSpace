package com.aamu.admin.main.serviceimpl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.MainService;

@Service
public class MainServiceImpl implements MainService{
	
	@Autowired
	private MainDAO dao;
	
	@Override
	public int usersTotalCount() {
		
		return dao.usersTotalCount();
	}

	@Override
	public int usersTodayCount(Date today) {
		
		return dao.usersTodayCount(today);
	}

	@Override
	public int placesTotalCount(Map map) {
	
		return dao.placesTotalCount(map);
	}
	
	
}
