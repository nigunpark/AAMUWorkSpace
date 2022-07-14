package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public interface BBSService {
	//글 목록
	List<BBSDTO> bbsSelectList();
	
	//글 목록_사진 뿌려주기
	List bbsSelectPhotoList(int rbn);
	
	//글 작성
	int bbsInsert(Map map);
	
	//
	
	//글 수정
	int bbsUpdate(Map map);
	
	//글 삭제
	
	//글 등록일자
	
	/*----------------------------------------------------------*/

	//리뷰 목록
	List<ReviewDTO> reviewSelectList(Map map);
	
	//리뷰 목록_리뷰 하나 뿌려주기
	String reviewSelectOne(String rno);
	
	//평점 평균 
	double getRatingAverage(int rate);
	
	//평점 평균 반영 및 업데이트
	
}