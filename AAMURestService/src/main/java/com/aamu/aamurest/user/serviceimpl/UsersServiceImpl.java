package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.user.service.UsersService;
import com.aamu.aamurest.util.FileUploadUtil;
import com.aamu.aamurest.util.UserUtil;

@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	private UsersDAO dao;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Override
	public int joinUser(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx->{
			dao.joinUser(map);
			for(Object theme :(List)map.get("theme")) {
				String themeid = UserUtil.changeTheme(theme.toString());
				System.out.println(themeid);
				map.put("themeid", themeid);
				dao.insertTheme(map);
			
			}
			return dao.insertAuth(map);
		});
		return affected;
	}

	@Override
	public UsersDTO selectOneUser(Map map) {

		return dao.selectOneUser(map);
	}

	@Override
	public int updateUser(Map map) {

		return dao.updateUser(map);
	}

	@Override
	public int checkId(String id) {

		return dao.checkId(id);
	}

	@Override
	public List<Map> selectAllTheme() {
		
		return dao.selectAllTheme();
	}



}
