package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.AnswerDTO;

@Repository
public class AnswerDAO {

	@Autowired
	private SqlSessionTemplate template;

	// 댓글 목록
	public List<AnswerDTO> selectList(Map map) {
		return template.selectList("answerSelectList", map);
	}

	// 이름 찾기
	public String findNameByKey(Map map) {
		return template.selectOne("answerFindNameByKey", map);
	}

	// 댓글 등록
	public int insert(Map map) {
		template.insert("answerInsert", map);
		return Integer.parseInt(map.get("ano").toString());
	}

	// 댓글 수정
	public int update(Map map) {
		return template.update("answerUpdate", map);
	}

	// 댓글 삭제
	public int delete(Map map) {
		return template.delete("answerDelete", map);
	}

	// 특정 댓글 삭제
	public int deleteByNo(Map map) {
		return template.delete("answerDeleteByNo", map);
	}

	// 댓글 전체 삭제
	public int answerAllDelete(Map map) {
		return template.delete("answerDeleteByNo", map);
	}

}