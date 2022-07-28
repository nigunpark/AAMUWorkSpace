package com.aamu.admin.main.serviceimpl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class BBSAdminDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//전체 게시글 목록
	
	
	//전체 게시글 수
	public int bbsGetTotalRecordCount(Map map) {
		return template.selectOne("bbsGetTotalRecordCount", map);
	}
	
	//게시글 삭제
	public int bbsdelete(Map map) {
		return template.delete("bbsDelete",map);
	}
	
	//전체 리뷰 목록
	
	
	//리뷰 삭제
	public int reviewdelete(Map map) {
		return template.delete("reviewDelete",map);
	}
	
}
