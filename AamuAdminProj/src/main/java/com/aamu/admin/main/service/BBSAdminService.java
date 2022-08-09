package com.aamu.admin.main.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BBSAdminService {
	
	//게시글 목록
	ListPagingData<BBSAdminDTO> bbsAdminSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 게시글 수
	int bbsGetTotalRecordCount(Map map);
	
	//게시글 삭제
	int bbsDelete(Map map);
	
	//리뷰 목록
	ListPagingData<ReviewAdminDTO> reviewAdminSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 리뷰 수
	int reviewGetTotalRecordCount(Map map);
	
	//리뷰 삭제
	int reviewAdminDelete(Map map);
	
	
	/*---------------------------------------------------------------*/
	/*
	//게시판 통계
	Map bbsTotal();
	
	//게시판 통계_베스트 게시글
	List<BBSAdminDTO> bbsBestList();
	
	//게시판 통계_글쓴이 프로필 사진 뿌려주기
	String bbsSelectProfile(String id);	
	*/
}
