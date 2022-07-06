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
	
	//글 목록용_댓글 하나 뿌려주기
	public CommuCommentDTO commuCommentSelectOne(String lno) {
		return template.selectOne("commuCommentSelectOne",lno);
	}
	
	//글 목록용_사진 뿌려주기
	public List commuSelectPhotoList(String lno){
		return template.selectList("commuSelectPhotoList",lno);
	}
	
	//글 생성용
	public int commuInsert(Map map) {
		System.out.println("(CommuDAO_commuInsert)map:"+map);
		return template.insert("commuInsert",map);
	}
	
	//글 생성용_사진 저장
	public int photoInsert(Map map) {
		System.out.println("(CommuDAO_photoInsert)map:"+map);
		return template.insert("commuPhotoInsert",map);
	}
	
	//글 생성용_장소 저장
	public int placeInsert(Map map) {
		return template.insert("commuPlaceInsert",map);
	}
	
	//글 생성용_장소 뿌려주기
	public List<Map> commuPlaceList(Map map) {
		System.out.println("map:"+map);
		return template.selectList("commuPlaceList",map);
	}
	
	//글 수정용
	public int commuUpdate(CommuDTO dto) {
		return template.update("commuUpdate",dto);
	}

	//글 하나 뿌려주는 용
	public CommuDTO commuSelectOne(String lno) {
		return template.selectOne("commuSelectOne",lno);
	}
	
	
	
	/*
	//글 수정용_장소 수정
	public int placeUpdate(CommuDTO dto) {
		return template.update("commuPlaceUpdate",dto);
	}
	*/

}
