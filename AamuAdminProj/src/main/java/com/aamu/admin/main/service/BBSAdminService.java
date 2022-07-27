package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BBSAdminService {
	
	//전체 게시글 목록
	ListPagingData<BBSAdminDTO> bbsSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 게시글 수
	int bbsGetTotalRecordCount(Map map);
	
	//게시글 하나 보기
	BBSAdminDTO bbsAdimSelectOne(Map map);
	
	//게시글 삭제
	int bbsDelete(Map map);
	
	//전체 리뷰 목록
	ListPagingData<ReviewAdminDTO> reviewSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//리뷰 삭제
	int reviewDelete(Map map);
}
