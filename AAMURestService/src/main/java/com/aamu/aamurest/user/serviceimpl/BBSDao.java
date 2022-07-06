package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;

@Repository
public class BBSDao {
	
	@Autowired
	private SqlSessionTemplate template;
	
	public List<BBSDTO> bbsSelectList(){
		return template.selectList("bbsSelectList");
	}


	
}
