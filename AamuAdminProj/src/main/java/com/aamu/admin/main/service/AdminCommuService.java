package com.aamu.admin.main.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AdminCommuService {
	
	//목록용
	ListPagingData<AdminCommuDTO> selectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 게시물 수 뿌려주기
	int commuGetTotalRecordCount(Map map);
	
	//글 삭제
	int commuDelete(Map map);
	
	

}
