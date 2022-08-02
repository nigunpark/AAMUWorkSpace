package com.aamu.aamurest.user.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface NoticeService<T> {

	// 게시물 목록
	List<T> noticeSelectList(Map map);

	// 게시물 수 카운트
	int noticeSearchTotalCount(Map map);

	// 게시물 검색
	List<String> noticeSearachList(Map map);

	// 상세 보기
	NoticeDTO noticeSelectOne(Map map);

	// 게시물 등록
	int noticeInsert(Map map);

	// 게시물 수정
	int noticeUpdate(Map map);

	// 게시물 삭제
	int noticeDelete(Map map);

}