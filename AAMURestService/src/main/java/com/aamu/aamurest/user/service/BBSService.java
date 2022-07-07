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

	
	//글 생성_수정
	
	//글 생성_삭제
	
	//별점 등록
	
	//별점 평균
	
}