package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//커뮤니티 글목록
	List<T> commuSelectList();
	
	//커뮤니티 글생성
	int commuInsert(Map map);

}
