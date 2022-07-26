package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface UsersService {
	//전체 회원 뿌려주기
	ListPagingData<UsersDTO> usersSelectList(Map map,HttpServletRequest req,int nowPage);
}
