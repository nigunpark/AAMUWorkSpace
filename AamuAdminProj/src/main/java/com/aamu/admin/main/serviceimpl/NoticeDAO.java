package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.NoticeDTO;

@Repository
public class NoticeDAO {
	
	@Autowired
	private SqlSessionTemplate template;

	//전체 글 뿌려주기
	public List<NoticeDTO> noticeSelectList(Map map){
		List<NoticeDTO> records = template.selectList("noticeSelectList",map);
		return records;
	}
	
	//글 전체 레코드수
	public int noticeGetTotalRecordCount(Map map) {
		return template.selectOne("noticeGetTotalRecordCount", map);
	}
	
	// 글 등록
	public int noticeWrite(Map map) {		
		return template.insert("noticeWrite",map);
	}
	
	// 글 상세 보기
	public NoticeDTO selectOne(Map map) {		
		return template.selectOne("noticeSelectOne", map);
	}

	// 조회수
	public int noticeCount(Map map) throws Exception {		
		return template.update("noticeCount", map);
	}
	
	//글 삭제
	public int noticeDelete(Map map) {
		return template.delete("noticeDelete",map);
	}

	public int noticeViewDelete(Map map) {
		return template.delete("noticeDelete",map);
		
	}

	public int noticeEdit(Map map) throws Exception {
		return template.update("noticeEdit",map);
	}

}