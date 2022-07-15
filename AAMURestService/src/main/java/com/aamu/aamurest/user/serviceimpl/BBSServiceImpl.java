package com.aamu.aamurest.user.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;
import com.aamu.aamurest.user.service.ReviewDTO;

@Service
public class BBSServiceImpl implements BBSService{
	
	@Autowired
	private BBSDAO dao;
	
	//글 목록
	@Override
	public List<BBSDTO> bbsSelectList() {
		return dao.bbsSelectList();
	}
	
	//글 목록_사진 뿌려주기
	@Override
	public List bbsSelectPhotoList(int rbn) {
		return dao.bbsSelectPhotoList(rbn);
	}
	//글 등록
	@Override
	public int bbsInsert(Map map) {
		
		int bbsaffected=dao.bbsInsert(map);
		//사진
		int photoAffected=0;
		List<String> lists= (List<String>) map.get("photolist");
		System.out.println("(bbsServiceImpl)lists:"+lists);
		for(String photo:lists) {
			Map photomap = new HashMap();
			photomap.put("rbn", map.get("rbn"));
			photomap.put("photo", photo);
			photoAffected+=dao.photoInsert(photomap);
			System.out.println("(CommuServiceImpl)photoAffected:"+photoAffected);
		}
		if(bbsaffected==1 && photoAffected==((List)map.get("photolist")).size()) 
			return 1;
		else
			return 0;
	}
	
	//글 수정
	@Override
	public int bbsUpdate(Map map) {
		return dao.bbsUpdate(map);
	}
	
	//글 삭제
	@Override
	public int bbsDelete(String rbn) {
		return dao.bbsDelete(rbn);
	}
	
	//글 등록일자
	
	/*---------------------------------------------------*/
	//리뷰 목록
	@Override
	public List<ReviewDTO> reviewSelectList(Map map) {
		return dao.reviewSelectList(map);
	}
	
	//리뷰 등록
	@Override
	public int reviewInsert(Map map) {
		return dao.reviewInsert(map);
	}
	
	//리뷰 목록_리뷰 하나 뿌려주기
	public String reviewSelectOne(String rno) {
		return dao.reviewSelectOne(rno);
	}
	
	@Override
	//평점 평균 반영하기
	public int updateRating(ReviewDTO rate) {
		return dao.updateRating(rate);
	}

	@Override
	public double getRatingAverage(int ratingavg) {
		return dao.getRatingAverage(ratingavg);
	}


}
	