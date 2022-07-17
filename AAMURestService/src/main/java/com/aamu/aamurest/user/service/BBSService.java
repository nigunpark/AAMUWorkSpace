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
	
	//글 등록
	int bbsInsert(Map map);
	
	//글 수정
	int bbsUpdate(Map map);
	
	//글 삭제
	int bbsDelete(Map map);
	
	//글 상세보기_모든 리뷰 보기
	List<ReviewDTO> reviewList(String rno);
	
	/*----------------------------------------------------*/

	//리뷰 목록
	List<ReviewDTO> reviewSelectList(Map map);
	
	//리뷰 등록
	int reviewInsert(Map map);
	
	//리뷰 수정
	int reviewUpdate(Map map);
	
	//리뷰 삭제
	int reviewDelete(Map map);
	
	//평점 평균 구하기 (소수점 한자리까지)
	Double getRatingAverage(int rno);
	
	//평점 평균 반영하기
	int updateRating(Map map);
	
	int updateRating(ReviewDTO rate);
	
	
	
}