package com.aamu.aamurest.user.serviceimpl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.ReviewDTO;

@Repository
public class ReviewDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//리뷰 목록
	public List<ReviewDTO> reviewSelectList() {
		return template.selectList("reviewSelectList");
	}
	
	//리뷰 목록_리뷰 하나 뿌려주기
	public String reviewSelectOne(String rno) {
		return template.selectOne("reviewSelectOne",rno);
	}
	
	//
}
