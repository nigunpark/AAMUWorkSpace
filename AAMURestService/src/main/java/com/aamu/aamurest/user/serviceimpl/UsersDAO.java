package com.aamu.aamurest.user.serviceimpl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.UsersDTO;

@Repository
public class UsersDAO {
	
	@Autowired
	SqlSessionTemplate template;
	
	public int joinUser(UsersDTO dto) {
	
		return template.insert("joinUser",dto);
	}

	public UsersDTO selectOneUser(Map map) {
		
		return template.selectOne("selectOneUser",map);
	}

	public int updateUser(UsersDTO dto) {
		
		return template.update("updateUser",dto);
	}

}
