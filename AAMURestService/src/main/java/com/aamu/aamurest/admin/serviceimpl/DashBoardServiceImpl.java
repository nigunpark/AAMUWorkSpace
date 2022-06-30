package com.aamu.aamurest.admin.serviceimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.admin.service.DashBoardService;

@Service
public class DashBoardServiceImpl implements DashBoardService {
	
	@Autowired
	private DashBoardDAO dao;
	
	@Override
	public int userTotalCount() {
		
		return dao.userTotalCount();
	}

	@Override
	public int userJoinCount(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commuCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int bbsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int userRating() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map categoryCount() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
