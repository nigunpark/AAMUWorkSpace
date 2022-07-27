package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface QNAService<T> {

	// 목록
	ListPagingData<T> selectList(Map map, HttpServletRequest req, int nowPage);

	// 게시물수
	int qnaGetTotalRecordCount(Map map);

	// 읽기
	T selectOne(Map map);

	// 쓰기
	int qnaWrite(Map map);

	// 수정
	int qnaEdit(Map map) throws Exception;

	// 삭제
	int qnaDelete(Map map);

	// 조회수
	int qnaCount(Map map) throws Exception;
	
	// 키로 이름 찾는 메소드
	String findNameByKey(Map map);

}