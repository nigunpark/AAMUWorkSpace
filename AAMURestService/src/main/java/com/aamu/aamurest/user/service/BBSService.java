package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public interface BBSService {
	//글 목록
	List<BBSDTO> bbsSelectList(Map map);

	//글 목록_사진 뿌려주기
	List bbsSelectPhotoList(int rbn);

	//글 하나 선택
	BBSDTO bbsSelectOne(int rbn);
	
	//글 하나 주인
	String bbsSelectUserID(int rbn);

	//글 등록
	int bbsInsert(Map map);

	//글 수정
	int bbsUpdate(Map map);

	//글 삭제
	int bbsDelete(Map map);
	
	//글 북마크
	boolean bbsBookmark(Map map);
	
	//글 북마크 목록
	List<BBSDTO> bbsBookmarkList(Map map);
	
	//글 검색 목록
	List<String> bbsSearchList(Map map);

	/*----------------------------------------------------*/

	//글 상세보기_모든 리뷰 보기
	List<ReviewDTO> reviewSelectList(int rbn);
	
	//리뷰 등록
	int reviewInsert(Map map);
	
	/*
	//리뷰 수정
	int reviewUpdate(Map map);
	*/
	
	//리뷰 삭제
	int reviewDelete(Map map);
	
	//평점 업데이트
	int updateRate(Map map);
	
	//북마크 여부
	boolean checkBookmark(Map map);

}