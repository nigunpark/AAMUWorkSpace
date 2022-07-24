package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AnswerService {

	// 전체
	ListPagingData<AnswerDTO> noticeSelectList(Map map, HttpServletRequest req, int nowPage);

	// 쓰기
	int noticeWrite(Map map);

	// 삭제
	int noticeDelete(Map map);

	// 수정
	int noticeEdit(Map map) throws Exception;

}