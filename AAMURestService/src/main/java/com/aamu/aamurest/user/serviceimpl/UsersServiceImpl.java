package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.Vector;

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

	@Override
	public int updateTheme(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx->{
			dao.deleteTheme(map);
			List<Map> list=(List)map.get("themes");
			for(Map listMap : list) {
				map.put("themeid", UserUtil.changeTheme(listMap.get("theme").toString()));
				System.out.println(map);
				dao.insertTheme(map);
			}
			int result =1;
			return result;
		});
		
		return affected;
	}

	@Override
	public List selectUserTheme(Map map) {
		List<String> list = dao.selectUserTheme(map);
		List<Map> themeList = new Vector<>();
		for(String themeid:list) {
			Map themeMap = dao.selectOneTheme(themeid);
			themeList.add(themeMap);
		}
		return themeList;
	}



}
