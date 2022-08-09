package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ThemeService {
	//전체 글 뿌려주기
	ListPagingData<ThemeDTO> themeSelectList(Map map,HttpServletRequest req,int nowPage);
	
	//전체 테마 수 뿌려주기
	int themeGetTotalRecordCount(Map map);
	
	//테마 이미지
	String getThemeImage(String themeid);
}
