package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface UsersService {
	//전체 회원 뿌려주기
	ListPagingData<UsersDTO> usersSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 회원 수 뿌려주기
	int usersGetTotalRecordCount(Map map);
	
	//유저 프로필
	String usersSelectUserProf(String id);
	
	//회원 삭제
	int usersStop(Map map);
}
