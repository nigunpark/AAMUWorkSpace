package com.aamu.admin.main.serviceimpl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO {
	@Autowired
	private SqlSessionTemplate template;

}
