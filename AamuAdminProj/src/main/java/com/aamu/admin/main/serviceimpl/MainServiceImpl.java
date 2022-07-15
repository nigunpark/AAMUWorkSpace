package com.aamu.admin.main.serviceimpl;

import java.util.Date;
import java.util.List;
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
	public int usersTodayCount(String today) {
		
		return dao.usersTodayCount(today);
	}

	@Override
	public int placesTotalCount(Map map) {
	
		return dao.placesTotalCount(map);
	}

	@Override
	public int selectPeriod(Map map) {
		
		return dao.selectPeriod(map);
	}

	@Override
	public int selectJoin(String day) {
		
		return dao.selectJoin(day);
	}

	@Override
	public int selectUsers(Map map) {
		
		return dao.selectUsers(map);
	}

	@Override
	public String selectDate(String day) {
		
		return dao.selectDate(day);
	}
	
	
	
	
}
