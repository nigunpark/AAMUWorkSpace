package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface QNAService {

	// 전체
	ListPagingData<QNADTO> qnaSelectList(Map map, HttpServletRequest req, int nowPage);

	// 게시물수
	int qnaGetTotalRecordCount(Map map);

	// 읽기
	QNADTO selectOne(Map map);

	// 삭제
	int qnaDelete(Map map);

	// 조회수
	int qnaCount(Map map) throws Exception;

}