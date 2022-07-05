package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//글 목록용
	List<T> commuSelectList();
	
	//글 목록용_댓글 한개 가져오기
	CommuCommentDTO commuCommentSelectOne(String cno);
	
	//글 목록용_사진 가져오기
	List commuSelectPhotoList(String cno);
	
	//커뮤니티 글생성
	int commuInsert(Map map);

}
