package com.aamu.admin.main.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AdminCommuService {
	
	//전체 글 뿌려주기
	ListPagingData<AdminCommuDTO> commuSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 게시물 수 뿌려주기
	int commuGetTotalRecordCount(Map map);
	
	//글 삭제
	int commuDelete(Map map);
	
	//댓글 뿌려주기
	ListPagingData<AdminCommuCommentDTO> commuCommentList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 게시물 수 뿌려주기
	int commuCommentGetTotalRecordCount(Map map);
	
	//글 삭제
	int commuCommentDelete(Map map);
	
	/////////////////////////////////////////////////////////////////////
	
	//커뮤니티 통계
	Map commuTotal();
	
	//커뮤니티 통계_베스트 글쓴이
	List<AdminCommuDTO> bestUsersList();
	

}
