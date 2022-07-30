package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BBSAdminDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//전체 게시글 목록
	public List bbsSelectList(Map map) {
		return template.selectList("bbsSelectList",map);
	}
	
	//전체 게시글 수
	public int bbsGetTotalRecordCount(Map map) {
		return template.selectOne("bbsGetTotalRecordCount", map);
	}
	
	//게시글 삭제
	public int bbsDelete(Map map) {
		return template.delete("bbsDelete",map);
	}
	
	//전체 리뷰 목록
	public List reviewSelectList(Map map) {
		return template.selectList("reviewSelectList",map);
	}
	
	//전체 리뷰 수
	public int reviewGetTotalRecordCount(Map map) {
		return template.selectOne("reviewGetTotalRecordCount",map);
	}
	
	//리뷰 삭제
	public int reviewDelete(Map map) {
		return template.delete("reviewDelete",map);
	}
	
}
