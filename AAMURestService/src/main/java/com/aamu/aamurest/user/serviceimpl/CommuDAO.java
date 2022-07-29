package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;

@Repository
public class CommuDAO {

	@Autowired
	private SqlSessionTemplate template;

	//글 목록용
	public List<CommuDTO> commuSelectList(Map map){
		return template.selectList("commuSelectList",map);
	}

	//글 목록용_댓글 하나 뿌려주기
	public CommuCommentDTO commuCommentSelectOne(String lno) {
		return template.selectOne("commuCommentSelectOne",lno);
	}

	//글 목록용_사진 뿌려주기
	public List commuSelectPhotoList(String lno){
		return template.selectList("commuSelectPhotoList",lno);
	}
	
	//글 목록용_검색 총 게시물
	public int commuTotalCount(Map map) {
		return template.selectOne("commuTotalCount",map);
	}
	
	//글 목록용_CommuTag에 레코드값
	public int selectCountCommuTag(String lno) {
		return template.selectOne("selectCountCommuTag",lno);
	}
	
	//글 목록용_tname 얻기
	public List<String> commuSelectTagName(String lno){
		return template.selectList("commuSelectTagName",lno);
	}
	
	//글 검색용
	public List<String> commuSearachList(Map map){
		System.out.println("dao map:"+map.get("searchColumn"));
		System.out.println("dao map:"+map.get("table"));
		System.out.println("dao map:"+map.get("searchWord"));
		return template.selectList("commuSearachList",map);
	}

	//글 생성용
	public int commuInsert(Map map) {
		return template.insert("commuInsert",map);
	}

	//글 생성용_사진 저장
	public int photoInsert(Map map) {
		return template.insert("commuPhotoInsert",map);
	}

	//글 생성용_장소 저장
	public int placeInsert(Map map) {
		return template.insert("commuPlaceInsert",map);
	}

	//글 생성용_장소 뿌려주기
	public List<Map> commuPlaceList(Map map) {
		return template.selectList("commuPlaceList",map);
	}

	//글 생성용_방금 insert된 글 다시 보내기
	/*
	public CommuDTO commuSelectAfterInsert() {
		return template.selectOne("commuSelectAfterInsert");
	}
	*/
	
	//글 생성용_태그 뿌려주기!!!!!!!!!!!!!!!!!!!
	public List<String> commuSelectTag(Map map) {
		return template.selectList("commuSelectTag",map);
	}
	
	//글 생성용_커뮤태그 테이블에 넣기!!!!!!!!!!!!!!!!!!!!!!1
	public int commuInsertCommuTag(Map map) {
		return template.insert("commuInsertCommuTag",map);
	}
	
	//글 생성용_태그 테이블에 넣기 !!!!!!!!!!!!!!!!!!
	public int commuInsertTags(Map map) {
		return template.insert("commuInsertTags",map);
	}
	
	//글 생성용_commuTag 테이블에 넣기!!!!!!!!!!!!!!!!~~~~~~~~~~~~~~~~~~
	public int commuTagInsert(Map map) {
		return template.insert("commuTagInsert",map);
	}
	
	//글 생성용_tno 가져오기
	public int selectTno(Map map) {
		System.out.println("(dao)tname에 들어있니?"+map.get("tname"));
		return template.selectOne("selectTno",map);
	}

	//글 하나 뿌려주는 용
	public CommuDTO commuSelectOne(String lno) {
		return template.selectOne("commuSelectOne",lno);
	}

	//글 하나 뿌려주는 용_모든 댓글 뿌려주기
	public List<CommuCommentDTO> commuCommentList(String lno){
		return template.selectList("commuCommentList",lno);
	}

	//글 하나 뿌려주는 용_프로필 뿌려주기
	public String commuSelectUserProf(String id) {
		return template.selectOne("commuSelectUserProf",id);
	}

	//글 수정용
	public int commuUpdate(Map map) {
		return template.update("commuUpdate",map);
	}

	//글 수정용_commuplace 수정
	public int commuPlaceUpdate(Map map) {
		return template.update("commuPlaceUpdate",map);
	}

	//글 삭제용
	public int commuDelete(Map map) {
		return template.delete("commuDelete",map);
	}

	//댓글 생성용
	public int commuCommentInsert(Map map) {
		return template.insert("commuCommentInsert",map);
	}

	//댓글 생성용_Rcount컬럼 +1
	public int commuRcPlusUpdate(Map map) {
		return template.update("commuRcPlusUpdate",map);
	}

	//댓글 수정용
	public int commuCommentUpdate(Map map) {
		return template.update("commuCommentUpdate",map);
	}

	//댓글 삭제용
	public int commuCommentDelete(Map map) {
		return template.delete("commuCommentDelete",map);
	}

	//댓글 삭제용_Rcount컬럼 -1
	public int commuRcMinusUpdate(Map map) {
		return template.update("commuRcMinusUpdate",map);
	}

	//글 좋아요_
	public int commuLikeSelect(Map map) {
		return template.selectOne("commuLikeSelect",map);
	}

	//글 좋아요_insert(likeboard테이블)
	public int commuLikeInsert(Map map) {
		return template.insert("commuLikeInsert",map);
	}

	//글 좋아요_update(community테이블의 likecount+1)
	public int commuLikePlusUpdate(Map map) {
		return template.update("commuLikePlusUpdate",map);
	}

	//글 좋아요_select(community테이블의 likecount)
	public int commuLikecountSelect(Map map) {
		return template.selectOne("commuLikecountSelect",map);
	}

	//글 좋아요취소_delete(likeboard테이블)
	public int commuLikeDelete(Map map) {
		return template.delete("commuLikeDelete",map);
	}

	//글 좋아요취소_update(community테이블의 likecount+1)
	public int commuLikeMinusUpdate(Map map) {
		return template.delete("commuLikeMinusUpdate",map);
	}


}
