package com.aamu.aamurest.user.serviceimpl.admin;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.admin.AnswerDTO;
import com.aamu.aamurest.user.service.admin.QNADTO;

@Repository
public class QNADAO {

	@Autowired
	private SqlSessionTemplate template;

	// 게시물 목록
	public List<QNADTO> qnaSelectList(Map map) {
		System.out.println("목록: " + map);
		return template.selectList("qnaSelectList", map);
	}

	// 게시물 수 카운트
	public int qnaSearchTotalCount(Map map) {
		return template.selectOne("qnaSearchTotalCount", map);
	}

	// 게시물 검색
	public List<String> qnaSearachList(Map map) {
		System.out.println("dao map:" + map.get("searchColumn"));
		System.out.println("dao map:" + map.get("table"));
		System.out.println("dao map:" + map.get("searchWord"));
		return template.selectList("qnaSearachList", map);
	}

	// 상세 보기
	public QNADTO qnaSelectOne(Map map) {
		return template.selectOne("qnaSelectOne", map);
	}

	// 게시물 등록
	public int qnaInsert(Map map) {
		return template.insert("qnaInsert", map);
	}

	// 게시물 수정
	public int qnaUpdate(Map map) {
		return template.update("qnaUpdate", map);
	}

	// 게시물 삭제
	public int qnaDelete(Map map) {
		return template.delete("qnaDelete", map);
	}

	// 댓글 목록
	public List<AnswerDTO> answerList(String qno) {
		return template.selectList("answerList", qno);
	}

	// 댓글 수 카운트
	public int answerSearchTotalCount(Map map) {
		return template.selectOne("answerSearchTotalCount", map);
	}

	// 댓글 등록
	public int answerInsert(Map map) {
		return template.insert("answerInsert", map);
	}

	// 댓글 수정
	public int answerUpdate(Map map) {
		return template.update("answerUpdate", map);
	}

	// 댓글 삭제
	public int answerDelete(Map map) {
		return template.delete("answerDelete", map);
	}

	// 댓글 전부 삭제
	public int answerAllDelete(Map map) {
		return template.delete("answerDeleteByNo", map);
	}

	// 이름 찾기
	public String findNameByKey(Map map) {
		return template.selectOne("answerFindNameByKey", map);
	}

}