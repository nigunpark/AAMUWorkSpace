package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ThemeService {
	//전체 글 뿌려주기
	ListPagingData<ThemeDTO> themeSelectList(Map map,HttpServletRequest req,int nowPage);
}
