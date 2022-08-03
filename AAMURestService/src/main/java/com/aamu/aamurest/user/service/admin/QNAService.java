package com.aamu.aamurest.user.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface QNAService<T> {

	// 게시물 목록
	List<T> qnaSelectList(Map map);

	// 게시물 수 카운트
	int qnaSearchTotalCount(Map map);

	// 게시물 검색
	List<String> qnaSearachList(Map map);

	// 상세 보기
	QNADTO qnaSelectOne(Map map);

	// 게시물 등록
	int qnaInsert(Map map);

	// 게시물 수정
	int qnaUpdate(Map map);

	// 게시물 삭제
	int qnaDelete(Map map);

	// 댓글 생성
	int answerInsert(Map map);

	// 댓글 수정
	int answerUpdate(Map map);

	// 댓글 삭제
	int answerDelete(Map map);
	
	// 댓글 전부 삭제
	int answerAllDelete(Map map);
	
	// 게시물 목록 - 게시물 수 카운트
	int answerSearchTotalCount(Map map);

	/*
	// 게시물 삭제
	int qnaDelete(String qno);
	*/

}