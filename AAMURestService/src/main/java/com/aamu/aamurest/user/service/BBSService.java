package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public interface BBSService {
	//글 목록
	List<BBSDTO> bbsSelectList();
	
	//글 목록_사진 뿌려주기
	List bbsSelectPhotoList(int rbn);
	
	//글 등록
	int bbsInsert(Map map);
	
	//글 수정
	int bbsUpdate(Map map);
	
	//글 삭제
	
	//글 등록일자

	
	
}