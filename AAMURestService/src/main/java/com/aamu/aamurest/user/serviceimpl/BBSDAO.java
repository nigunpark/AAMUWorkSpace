package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.service.RouteDTO;

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

	//글 하나 선택
	public BBSDTO bbsSelectOne(int rbn) {
		return template.selectOne("bbsSelectOne",rbn);
	}

	//글 등록
	public int bbsInsert(Map map) {
		return template.insert("bbsInsert",map);
	}

	//글 등록_사진 저장
	public int photoInsert(Map photomap) {
		return template.insert("photoInsert",photomap);
	}

	//글 수정
	public int bbsUpdate(Map map) {
		return template.update("bbsUpdate",map);
	}

	//글 삭제
	public int bbsDelete(Map map) {
		return template.delete("bbsDelete",map);
	}

	/*----------------------------------------------------*/

	//글 상세보기_모든 리뷰 보기
	public List<ReviewDTO> reviewList(int rbn) {
		return template.selectList("reviewList",rbn);
	}

	//리뷰 등록
	public int reviewInsert(Map map) {
		return template.insert("reviewInsert",map);
	}
	
	/*
	//리뷰 수정
	public int reviewUpdate(Map map) {
		return template.update("reviewUpdate",map);
	}
	*/

	//리뷰 삭제
	public int reviewDelete(Map map) {
		return template.delete("reviewDelete",map);
	}
		
	//테마 등록
	public int themeInsert(BBSDTO theme) {
		return template.insert("themeInsert",theme);
	}
	
	//경로 가져오기
	public List<RouteDTO> selectRouteList(int rbn) {
		return template.selectList("selectRouteList",rbn);
	}


}

