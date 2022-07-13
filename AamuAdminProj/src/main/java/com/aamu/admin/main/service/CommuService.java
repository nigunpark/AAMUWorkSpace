package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface CommuService {
	
	//목록용]
	ListPagingData<CommuDTO> selectList(Map map,HttpServletRequest req,int nowPage);
	

}
