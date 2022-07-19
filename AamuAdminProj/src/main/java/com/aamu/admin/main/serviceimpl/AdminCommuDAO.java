package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.AdminCommuCommentDTO;
import com.aamu.admin.main.service.AdminCommuDTO;

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
		System.out.println("(adminCommuDTO)records:"+records);
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
	
	////////////////////////////////////////////////////////////
	public int commuMonthTotal(Map map) {
		System.out.println("map:"+map); //없음
		int affected=template.delete("commuMonthTotal",map);
		System.out.println("affected:"+affected); //-1
		return affected;
	}
	
	

}
