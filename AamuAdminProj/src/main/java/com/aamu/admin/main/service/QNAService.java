package com.aamu.admin.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface QNAService<T> {

	// 게시물 목록
	ListPagingData<T> selectList(Map map, HttpServletRequest req, int nowPage);

	// 게시물 수 카운트
	int qnaGetTotalRecordCount(Map map);

	// 상세 보기
	T selectOne(Map map);

	// 게시물 등록
	int qnaWrite(Map map);

	// 게시물 수정
	int qnaEdit(Map map) throws Exception;

	// 게시물 목록에서 게시물 삭제
	int qnaDelete(Map map);

	// 상세 보기에서 게시물 삭제
	int qnaViewDelete(Map map);

	// 조회 수 카운트
	int qnaCount(Map map) throws Exception;

	// 이름 찾기
	String findNameByKey(Map map);

	// 댓글 등록
	int answerWrite(Map map);

	// 댓글 수정
	int answerUpdate(Map map);

	// 댓글 삭제
	int answerDelete(Map map);

	// 댓글 전체 삭제
	int answerAllDelete(Map map);

}