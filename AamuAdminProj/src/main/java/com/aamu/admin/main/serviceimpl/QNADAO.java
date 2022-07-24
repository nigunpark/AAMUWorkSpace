package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.QNADTO;

@Repository
public class QNADAO {

	@Autowired
	private SqlSessionTemplate template;

	// 전체 글
	public List<QNADTO> qnaSelectList(Map map) {
		List<QNADTO> records = template.selectList("qnaSelectList", map);
		return records;
	}

	// 게시물수
	public int qnaGetTotalRecordCount(Map map) {
		return template.selectOne("qnaGetTotalRecordCount", map);
	}

	// 읽기
	public QNADTO selectOne(Map map) {
		return template.selectOne("qnaSelectOne", map);
	}

	// 조회수
	public int qnaCount(Map map) throws Exception {
		return template.update("qnaCount", map);
	}

	// 목록에서 삭제
	public int qnaDelete(Map map) {
		return template.delete("qnaDelete", map);
	}

	// 글에서 삭제
	public int qnaViewDelete(Map map) {
		return template.delete("qnaDelete", map);

	}
	
}