package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.AdminCommuCommentDTO;
import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.UsersDTO;

@Repository
public class AdminCommuDAO {
	@Autowired
	private SqlSessionTemplate template;
	
	//전체 글 뿌려주기
	public List<AdminCommuDTO> commuSelectList(Map map){
		List<AdminCommuDTO> records = template.selectList("commuSelectList",map);
		return records;
	}
	
	//글 전체 레코드수
	public int commuGetTotalRecordCount(Map map) {
		return template.selectOne("commuGetTotalRecordCount", map);
	}
	
	//글 삭제
	public int commuDelete(Map map) {
		return template.delete("commuDelete",map);
	}
	
	//댓글 뿌려주기
	public List<AdminCommuCommentDTO> commuCommentSelectList(Map map){
		List<AdminCommuCommentDTO> records = template.selectList("commuCommentSelectList",map);
		return records;
	}
	
	//댓글 전체 레코드수
	public int commuCommentGetTotalRecordCount(Map map) {
		return template.selectOne("commuCommentGetTotalRecordCount", map);
	}
	
	//댓글 삭제
	public int commuCommentDelete(Map map) {
		return template.delete("commuCommentDelete",map);
	}
	
	//사진 목록 가져오기
	public List<String> commuSelectPhotoList(Map map) {
		return template.selectList("commuSelectPhotoList",map);
	}
	
	////////////////////////////////////////////////////////////
	//커뮤니티 통계
	//월별 
	public int commuMonthTotal(Map map) {
		int affected=template.selectOne("commuMonthTotal",map);
		return affected;
	}
	
	//성별 게시물 비율
	public int genderPercent(Map map) {
		return template.selectOne("genderPercent", map);
	}
	
	//베스트 글쓴이 
	public List<AdminCommuDTO> bestUsersList(){
		List<AdminCommuDTO> records= template.selectList("bestUsersList");
		return records;
	}
	
	//프로필사진
	public String commuSelectUserProf(String id) {
		return template.selectOne("commuSelectUserProf",id);
		
	}

	
	

	

}
