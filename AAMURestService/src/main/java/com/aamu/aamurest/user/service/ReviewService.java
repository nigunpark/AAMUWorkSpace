package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
	
	//리뷰 목록
	List<ReviewDTO> reviewSelectList();
	
	//리뷰 목록_리뷰 하나 뿌려주기
	String reviewSelectOne(String rno);
}
