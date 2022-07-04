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
	
	//목록용
	public List<CommuDTO> commuSelectList(){
		return template.selectList("commuSelectList");
	}
	
	//댓글 하나 가져오는거
	public CommuCommentDTO commuCommentSelectOne(String cno) {
		return template.selectOne("commuCommentSelectOne",cno);
	}
	

	//글 저장용
	public int commuInsert(Map map) {
		return template.insert("commuInsert",map);
	}
	
	//photo 저장용
	public int photoInsert(Map map) {
		return template.insert("photoInsert",map);
	}
	
	//장소 저장용
	public int placeInsert(Map map) {
		return template.insert("placeInsert",map);
	}

}
