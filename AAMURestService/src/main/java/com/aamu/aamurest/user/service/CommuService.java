package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//글 목록용
	List<T> commuSelectList();
	
	//글 목록용_댓글 하나 뿌려주기
	CommuCommentDTO commuCommentSelectOne(String lno);
	
	//글 목록용_사진 뿌려주기
	List commuSelectPhotoList(String lno);
	
	//글 생성용
	int commuInsert(Map map);
	
	//!!!!!!!!!!글 생성용_장소 뿌려주기
	List<Map> commuPlaceList(Map map);
	
	//글 수정용
	int commuUpdate(CommuDTO dto);

}
