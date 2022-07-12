package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.CommuService;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.service.ReviewService;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDAO dao;
	
	//리뷰 목록
	@Override
	public List<ReviewDTO> reviewSelectList() {
		return dao.reviewSelectList();
	}
	
	//리뷰 목록_리뷰 하나 뿌려주기
	public String reviewSelectOne(String rno) {
		return dao.reviewSelectOne(rno);
	}


}
