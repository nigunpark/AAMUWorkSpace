package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.UsersDTO;

@Repository
public class UsersDAO {
	@Autowired
	private SqlSessionTemplate template;
	
	//전체 글 뿌려주기
	public List<UsersDTO> usersSelectList(Map map){
		List<UsersDTO> records = template.selectList("usersSelectList",map);
		return records;
	}
	
	//글 전체 레코드수
	public int usersGetTotalRecordCount(Map map) {
		return template.selectOne("usersGetTotalRecordCount", map);
	}

}
