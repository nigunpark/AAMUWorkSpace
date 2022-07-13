package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface CommuService {
	
	//목록용
	ListPagingData<CommuDTO> selectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 게시물 수 뿌려주기
	int commuGetTotalRecordCount(Map map);

}
