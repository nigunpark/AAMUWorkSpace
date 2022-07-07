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
	
	//글 목록
	public List<BBSDTO> bbsSelectList(){
		return template.selectList("bbsSelectList");
	}
	
	//글 목록_사진 뿌려주기
	public List bbsSelectPhotoList(int rbn) {
		return template.selectList("bbsSelectPhotoList",rbn);
	}
	

	
	

	

	
}
