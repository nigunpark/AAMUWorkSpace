package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//커뮤니티 글목록
	List<T> commuSelectList();
	
	//comment 한개 가져오기
	CommuCommentDTO commuCommentSelectOne(String cno);
	
	//사진
	List commuSelectPhotoList(String cno);
	
	//커뮤니티 글생성
	int commuInsert(Map map);

}
