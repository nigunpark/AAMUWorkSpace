package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface NoticeService {

	// 목록
	ListPagingData<NoticeDTO> noticeSelectList(Map map, HttpServletRequest req, int nowPage);

	// 게시물수
	int noticeGetTotalRecordCount(Map map);

	// 읽기
	NoticeDTO selectOne(Map map);

	// 쓰기
	int noticeWrite(Map map);

	// 수정
	int noticeEdit(Map map) throws Exception;

	// 삭제
	int noticeDelete(Map map);

	// 조회수
	int noticeCount(Map map) throws Exception;

}