package com.aamu.aamurest.user.serviceimpl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BBSDao {
	
	@Autowired
	private SqlSessionTemplate template;
	
	
}
