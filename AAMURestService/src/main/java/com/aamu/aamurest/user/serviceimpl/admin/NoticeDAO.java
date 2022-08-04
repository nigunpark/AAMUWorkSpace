package com.aamu.aamurest.user.serviceimpl.admin;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.admin.NoticeDTO;

@Repository
public class NoticeDAO {

	@Autowired
	private SqlSessionTemplate template;

	// 게시물 목록
	public List<NoticeDTO> noticeSelectList(Map map) {
		System.out.println("목록: " + map);
		return template.selectList("noticeSelectList", map);
	}

	// 게시물 수 카운트
	public int noticeSearchTotalCount(Map map) {
		return template.selectOne("noticeSearchTotalCount", map);
	}

	// 게시물 검색
	public List<String> noticeSearachList(Map map) {
		System.out.println("dao map:" + map.get("searchColumn"));
		System.out.println("dao map:" + map.get("table"));
		System.out.println("dao map:" + map.get("searchWord"));
		return template.selectList("noticeSearachList", map);
	}

	// 상세 보기
	public NoticeDTO noticeSelectOne(Map map) {
		return template.selectOne("noticeSelectOne", map);
	}

	// 게시물 등록
	public int noticeInsert(Map map) {
		return template.insert("noticeInsert", map);
	}

	// 게시물 수정
	public int noticeUpdate(Map map) {
		return template.update("noticeUpdate", map);
	}

	// 게시물 삭제
	public int noticeDelete(Map map) {
		return template.delete("noticeDelete", map);
	}

}