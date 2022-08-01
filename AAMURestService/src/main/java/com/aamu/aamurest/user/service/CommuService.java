package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//글 목록용
	List<T> commuSelectList(Map map);

	//글 목록용_댓글 하나 뿌려주기
	CommuCommentDTO commuCommentSelectOne(String lno);

	//글 목록용_사진 뿌려주기
	List commuSelectPhotoList(String lno);

	//글 목록용_좋아요 여부 뿌려주기
	//Boolean commuIsLike(Map map);
	
	//글 목록용_토탈카운트 
	int commuSearchTotalCount(Map map);
	
	//글 목록용_CommuTag에 레코드값
	int selectCountCommuTag(String lno);
	
	//글 목록용_tname 얻기
	List<String> commuSelectTagName(String lno);
	
	//글 검색용
	List<String> commuSearachList(Map map);

	//글 생성용
	int commuInsert(Map map);

	//글 생성용_장소 뿌려주기
	List<Map> commuPlaceList(Map map);

	//글 생성용_방금 insert된 글 다시 보내기 안쓰나?
	//CommuDTO commuSelectAfterInsert();
	
	//글 생성용_태그 뿌려주기 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	List<String> commuTag(Map map);

	//글 하나 뿌려주는 용
	CommuDTO commuSelectOne(Map map);

	//글 하나 뿌려주는 용_모든 댓글 뿌려주기
	List<CommuCommentDTO> commuCommentList(String lno);

	//글 하나 뿌려주는용
	String commuSelectUserProf(String id);

	//글 수정용
	int commuUpdate(Map map);

	//글 수정용_commuplace 수정 **위에랑 합침
	//int commuPlaceUpdate(Map map);

	//글 삭제용
	int commuDelete(String lno);

	//댓글 생성용
	int commuCommentInsert(Map map);

	//댓글 생성용_Rcount +1
	//int commuRcPlusUpdate(Map map);


	//int commuRcPlusUpdate(Map map);
	

	//댓글 수정용
	int commuCommentUpdate(Map map);

	//댓글 삭제용
	int commuCommentDelete(Map map);

	//댓글 삭제용_Rcount -1
	//int commuRcMinusUpdate(Map map);

	//int commuRcMinusUpdate(Map map);
	
	//글 좋아요 전체리스트
	//int commuLike(Map map);

	//글 좋아요
	Boolean commuLike(Map map);

	//글 좋아요_selectOne(community테이블의 likecount)
	int commuLikecountSelect(Map map);
	
	//팔로우, 팔로잉
	int commuFollower(Map map);
	
	//마이페이지용_id에 따른 
	List<CommuDTO> commuMyPageList(Map map);
	
	//마이페이지용_토탈카운트 셋팅 (해당 id의 총 글 갯수)
	int commuTotalCount(Map map);
	
	//마이페이지용_나를 팔로우하는 계정 수 셋팅
	int commuFollowerCount(Map map);
	
	//마이페이지용_내가 팔로잉하는 계정 수 셋팅
	int commuFollowingCount(Map map);
}
