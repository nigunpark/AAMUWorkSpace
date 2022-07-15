package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;
import com.aamu.aamurest.user.service.ReviewDTO;

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
	
	//글 삭제
	public int bbsDelete(String rbn) {
		return template.delete("bbsDelete",rbn);
	}
	/*----------------------------------------------------*/
	//리뷰 목록
	public List<ReviewDTO> reviewSelectList(Map map) {
		return template.selectList("reviewSelectList",map);
	}
	
	//리뷰 등록
	public int reviewInsert(Map map) {
		return template.insert("reviewInsert");
	}
	
	//리뷰 목록_리뷰 하나 뿌려주기
	public String reviewSelectOne(String rno) {
		return template.selectOne("reviewSelectOne",rno);
	}
	
	//평점 평균 반영 및 업데이트
	public int updateRating(ReviewDTO rate) {
		return template.update("updateRating",rate);
	}

	//평점 평균 구하기
	public double getRatingAverage(int ratingavg) {
		return ratingavg;
	}
	

	
	
	
}

