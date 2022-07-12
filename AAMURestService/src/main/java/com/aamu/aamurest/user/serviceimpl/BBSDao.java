package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;

@Repository
public class BBSDAO {
	
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
	
	//글 등록
	public int bbsInsert(Map map) {
		return template.insert("bbsInsert",map);
	}
	
	//글 등록_사진 저장
	public int photoInsert(Map photomap) {
		System.out.println("(bbsdao):"+photomap);
		return template.insert("photoInsert",photomap);
	}
	
	//글 수정
	public int bbsUpdate(Map map) {		
		return template.update("bbsUpdate",map);
	}
	
	
	
	
	
}

