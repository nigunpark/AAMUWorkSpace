package com.aamu.aamurest.user.serviceimpl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.CommuDTO;

@Repository
public class CommuDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//¸ñ·Ï
	public List<CommuDTO> commuSelectList(){
		return template.selectList("commuSelectList");
		
	}

}
