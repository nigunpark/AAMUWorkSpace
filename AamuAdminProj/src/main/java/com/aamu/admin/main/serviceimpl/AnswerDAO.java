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

	// 등록
	public int insert(Map map) {
		// 마이바티스의 insert는 무조건 영향받은 행의 수 반환
		template.insert("answerInsert", map);
		// 인자로 전달하는 Map에 새로 입력된 행의 키(번호)를 담을 수 있다
		return Integer.parseInt(map.get("ano").toString());
	}

	// 목록
	public List<AnswerDTO> selectList(Map map) {
		return template.selectList("answerSelectList", map);
	}

	public String findNameByKey(Map map) {
		return template.selectOne("answerFindNameByKey", map);
	}

	public int update(Map map) {
		return template.update("answerUpdate", map);
	}

	public int delete(Map map) {
		return template.delete("answerDelete", map);
	}

	public int deleteByNo(Map map) {
		return template.delete("answerDeleteByNo", map);
	}

	public void deletesByNo(int[] email) {
		template.delete("answerDeletesByNo", email);
	}

	public int answerAllDelete(Map map) {
		return template.delete("answerDeleteByNo", map);
	}

}