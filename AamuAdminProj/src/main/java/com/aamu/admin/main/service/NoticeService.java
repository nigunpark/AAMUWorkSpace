package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface NoticeService {

	// 게시물 목록
	ListPagingData<NoticeDTO> noticeSelectList(Map map, HttpServletRequest req, int nowPage);

	// 게시물 수 카운트
	int noticeGetTotalRecordCount(Map map);

	// 상세 보기
	NoticeDTO selectOne(Map map);

	// 게시물 등록
	int noticeWrite(Map map);

	// 게시물 수정
	int noticeEdit(Map map) throws Exception;

	// 게시물 삭제
	int noticeDelete(Map map);

	// 조회 수 카운트
	int noticeCount(Map map) throws Exception;

}