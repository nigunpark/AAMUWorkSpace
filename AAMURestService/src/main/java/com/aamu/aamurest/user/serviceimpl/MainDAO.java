package com.aamu.aamurest.user.serviceimpl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.AttractionDTO;

@Repository
public class MainDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	public int placeInsert(AttractionDTO dto) {
		
		
		return template.insert("placeInsert",dto);
	}

	public int infoInsert(AttractionDTO dto) {
		
		return template.insert("infoInsert",dto);
	}

	public int hotelInsert(AttractionDTO dto) {
		
		return template.insert("hotelInsert",dto);
	}

	public int dinerInsert(AttractionDTO dto) {
		return template.insert("dinerInsert",dto);
	}

}
