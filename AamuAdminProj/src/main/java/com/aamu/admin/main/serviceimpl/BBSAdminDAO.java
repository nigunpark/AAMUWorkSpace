package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.BBSAdminDTO;

@Repository
public class BBSAdminDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//전체 게시글 목록
	public List bbsSelectList(Map map) {
		return template.selectList("bbsAdminSelectList",map);
	}
	
	//전체 게시글 수
	public int bbsGetTotalRecordCount(Map map) {
		return template.selectOne("bbsGetTotalRecordCount", map);
	}
	
	//게시글 삭제
	public int bbsAdminDelete(Map map) {
		return template.delete("bbsAdminDelete",map);
	}
	
	//전체 리뷰 목록
	public List reviewAdminSelectList(Map map) {
		return template.selectList("reviewAdminSelectList",map);
	}
	
	//전체 리뷰 수
	public int reviewGetTotalRecordCount(Map map) {
		return template.selectOne("reviewGetTotalRecordCount",map);
	}
	
	//리뷰 삭제
	public int reviewAdminDelete(Map map) {
		return template.delete("reviewAdminDelete",map);
	}

	
	
	/*---------------------------------------------------------------*/
	/*
	//게시판 통계
	//월별 
	public int bbsMonthTotal(Map map) {
		int affected=template.selectOne("bbsMonthTotal",map);
		return affected;
	}
	
	//게시판 통계_베스트 게시글
	public List<BBSAdminDTO> bbsBestList(){
		List<BBSAdminDTO> records= template.selectList("bbsBestList");
		return records;
	}
	
	//게시판 통계_글쓴이 프로필 사진 뿌려주기
	public String bbsSelectProfile(String id) {
		return template.selectOne("bbsSelectProfile",id);
	}
	*/
	
}
