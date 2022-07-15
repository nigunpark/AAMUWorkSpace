package com.aamu.aamurest.user.serviceimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.user.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	private UsersDAO dao;
	
	@Override
	public int joinUser(UsersDTO dto) {
		
		return dao.joinUser(dto);
	}

	@Override
	public UsersDTO selectOneUser(Map map) {

		return dao.selectOneUser(map);
	}

	@Override
	public int updateUser(UsersDTO dto) {

		return dao.updateUser(dto);
	}

	@Override
	public int checkId(String id) {
	
		return dao.checkId(id);
	}
	
	

}
