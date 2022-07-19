package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface NoticeService {
	
	//전체 글 뿌려주기
	ListPagingData<NoticeDTO> noticeSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 게시물 수 뿌려주기
	int noticeGetTotalRecordCount(Map map);
	
	// 글 등록
	int noticeWrite(Map map);
	
	//글 삭제
	int noticeDelete(Map map);
	
}