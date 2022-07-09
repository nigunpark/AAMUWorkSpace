package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//글 목록용
	List<T> commuSelectList();
	
	//글 목록용_댓글 하나 뿌려주기
	CommuCommentDTO commuCommentSelectOne(String lno);
	
	//글 목록용_사진 뿌려주기
	List commuSelectPhotoList(String lno);
	
	//글 생성용
	int commuInsert(Map map);
	
	//글 생성용_장소 뿌려주기
	List<Map> commuPlaceList(Map map);
	
	//글 하나 뿌려주는 용
	CommuDTO commuSelectOne(String lno);
	
	//글 하나 뿌려주는 용_모든 댓글 뿌려주기
	List<CommuCommentDTO> commuCommentList(String lno);
	
	//글 수정용
	int commuUpdate(Map map);
	
	//글 수정용_commuplace 수정
	int commuPlaceUpdate(Map map);
	
	//글 삭제용
	int commuDelete(String lno);
	
	//댓글 생성용
	int commuCommentInsert(Map map);
	
	//댓글 수정용
	int commuCommentUpdate(Map map);
	
	//글 좋아요_insert likeboard 테이블
	int commuLikeInsert(Map map);
	
	//글 좋아요_update community테이블에 likecount
	int commuLikeUpdate(Map map);
	
	//글 좋아요 취소
	//int commuDislikeDelete(Map map);
	
}
