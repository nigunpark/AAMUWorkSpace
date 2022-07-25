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

	public int joinUser(Map map) {

		return template.insert("joinUser",map);
	}

	public UsersDTO selectOneUser(Map map) {

		return template.selectOne("selectOneUser",map);
	}

	public int updateUser(Map map) {

		return template.update("updateUser",map);
	}

	public int checkId(String id) {

		return template.selectOne("checkId",id);
	}

	public int insertAuth(Map map) {

		return template.insert("insertAuth", map);
	}

}
