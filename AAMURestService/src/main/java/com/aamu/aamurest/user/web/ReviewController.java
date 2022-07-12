package com.aamu.aamurest.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.serviceimpl.CommuServiceImpl;
import com.aamu.aamurest.user.serviceimpl.ReviewServiceImpl;

@RestController
public class ReviewController {
	
	@Autowired
	private ReviewServiceImpl reviewService;
	
	@Autowired
	private CommuServiceImpl commuService;
	
	//리뷰 목록
		@GetMapping("/review/selectList")
		public List<ReviewDTO> reviewSelectList(){
			List<ReviewDTO> list = reviewService.reviewSelectList();
			for(ReviewDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
				dto.setReview(reviewService.reviewSelectOne(dto.rno));
			}
			
			return list;
		}
}
