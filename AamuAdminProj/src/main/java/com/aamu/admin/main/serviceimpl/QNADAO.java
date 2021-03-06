package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.QNADTO;

@Repository
public class QNADAO {

	@Autowired
	private SqlSessionTemplate template;

	// 게시물 목록
	public List<QNADTO> qnaSelectList(Map map) {
		List<QNADTO> records = template.selectList("qnaSelectList", map);
		return records;
	}

	// 게시물 수 카운트
	public int qnaGetTotalRecordCount(Map map) {
		return template.selectOne("qnaGetTotalRecordCount", map);
	}

	// 상세 보기
	public QNADTO selectOne(Map map) {
		return template.selectOne("qnaSelectOne", map);
	}

	// 게시물 등록
	public int qnaWrite(Map map) {
		return template.insert("qnaWrite", map);
	}

	// 게시물 수정
	public int qnaEdit(Map map) throws Exception {
		return template.update("qnaEdit", map);
	}

	// 게시물 목록에서 삭제
	public int qnaDelete(Map map) {
		return template.delete("qnaDelete", map);
	}

	// 게시물 상세 보기에서 삭제
	public int qnaViewDelete(Map map) {
		return template.delete("qnaDelete", map);
	}

	// 조회 수 카운트
	public int qnaCount(Map map) throws Exception {
		return template.update("qnaCount", map);
	}

}