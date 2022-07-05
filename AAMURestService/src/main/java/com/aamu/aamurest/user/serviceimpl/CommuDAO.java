package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;

@Repository
public class CommuDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//글 목록용
	public List<CommuDTO> commuSelectList(){
		return template.selectList("commuSelectList");
	}
	
	//글 목록용_댓글 하나 가져오는거
	public CommuCommentDTO commuCommentSelectOne(String cno) {
		return template.selectOne("commuCommentSelectOne",cno);
	}
	
	//글 목록용_사진 리스트 가져오기
	public List commuSelectPhotoList(String cno){
		return template.selectList("commuSelectPhotoList",cno);
	}
	
	//글 저장용
	public int commuInsert(Map map) {
		System.out.println("map:"+map);
		return template.insert("commuInsert",map);
	}
	
	//글 저장용_photo 저장
	public int photoInsert(Map map) {
		return template.insert("commuPhotoInsert",map);
	}
	
	//글 저장용_장소 저장
	public int placeInsert(Map map) {
		return template.insert("commuPlaceInsert",map);
	}

}
