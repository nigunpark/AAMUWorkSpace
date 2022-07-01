package com.aamu.aamurest.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//커뮤니티 목록
	List<T> commuSelectList();
	

}
