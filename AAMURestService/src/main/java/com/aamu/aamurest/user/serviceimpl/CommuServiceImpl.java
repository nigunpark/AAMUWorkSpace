package com.aamu.aamurest.user.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.CommuService;

@Service("commuService")
public class CommuServiceImpl implements CommuService<CommuDTO>{
	@Autowired
	private CommuDAO dao;
	
	//글 목록용
	@Override
	public List<CommuDTO> commuSelectList() {
		return dao.commuSelectList();
	}
	
	//글 목록용_댓글 하나 뿌려주기
	@Override
	public CommuCommentDTO commuCommentSelectOne(String lno) {
		return dao.commuCommentSelectOne(lno);
	}
	
	//글 목록용_사진 뿌려주기
	@Override
	public List commuSelectPhotoList(String lno) {
		return dao.commuSelectPhotoList(lno);
	}
	
	//글 생성용
	@Override
	public int commuInsert(Map map) {
		//commu
		int commuaffected=dao.commuInsert(map);
		//사진
		int photoAffected=0;
		List<String> lists= (List<String>) map.get("photolist");
		System.out.println("(CommuServiceImpl)lists:"+lists);
		for(String photo:lists) {
			Map photomap = new HashMap();
			photomap.put("lno", map.get("lno"));
			photomap.put("photo", photo);
			photoAffected+=dao.photoInsert(photomap);
			System.out.println("(CommuServiceImpl)photoAffected:"+photoAffected);
		}
		//장소
		System.out.println("(CommuServiceImpl)map.get(\"contentid\"):"+map.get("contentid"));
		if(map.get("contentid")!=null) {
			int placeAffected=dao.placeInsert(map); 
			if(placeAffected ==0) return 0; 
		}
		
		if(commuaffected==1 && photoAffected==((List)map.get("photolist")).size()) 
			return 1;
		else
			return 0;
	}/////commuInsert
	
	//글 생성용_장소 뿌려주기
	@Override
	public List<Map> commuPlaceList(Map map) {
		return dao.commuPlaceList(map);
	}
	
	//글 하나 뿌려주는 용
	@Override
	public CommuDTO commuSelectOne(String lno) {
		return dao.commuSelectOne(lno);
	}
	
	//글 하나 뿌려주는 용_모든 댓글 뿌려주기
	@Override
	public List<CommuCommentDTO> commuCommentList(String lno) {
		return dao.commuCommentList(lno);
	}

	//글 수정용
	@Override
	public int commuUpdate(Map map) {
		return dao.commuUpdate(map);
	}
	
	//글 수정용_commuplace 수정
	@Override
	public int commuPlaceUpdate(Map map) {
		return dao.commuPlaceUpdate(map);
	}
	
	//글 삭제용
	@Override
	public int commuDelete(String lno) {
		return dao.commuDelete(lno);
	}
	
	//댓글 생성용
	@Override
	public int commuCommentInsert(Map map) {
		return dao.commuCommentInsert(map);
	}
	
	//댓글 생성용_Rcount컬럼 +1
	@Override
	public int commuRcPlusUpdate(Map map) {
		return dao.commuRcPlusUpdate(map);
	}
	
	//댓글 수정용
	@Override
	public int commuCommentUpdate(Map map) {
		return dao.commuCommentUpdate(map);
	}
	
	//댓글 삭제용
	@Override
	public int commuCommentDelete(Map map) {
		return dao.commuCommentDelete(map);
	}
	
	//댓글 삭제용_Rcount컬럼 -1
	@Override
	public int commuRcMinusUpdate(Map map) {
		return dao.commuRcMinusUpdate(map);
	}
	
	//글 좋아요
	@Override
	public int commuLike(Map map) {
		//likeboard테이블 select count(*)~where id=좋아요누른id
		int commuLikeSelectAffected=dao.commuLikeSelect(map); //1이면 좋아요누른id가 존재하는거 0이면 처음누른거
		int commuLikeInsertAffected, commuLikeUpdateAffected, commuLikeDeleteAffected=0;
		Map resultMap = new HashMap();
		//글 좋아요_insert(likeboard 테이블),update(community테이블의 likecount+1)
		if(commuLikeSelectAffected==0) {//0이면 해당id로 처음 누른거니까 likeboard-insert community-update 둘다 진행
			//likeboard테이블에 insert
			commuLikeInsertAffected=dao.commuLikeInsert(map);
			//community테이블의 likecount update 
			commuLikeUpdateAffected=dao.commuLikePlusUpdate(map);
			if(commuLikeInsertAffected==1 && commuLikeUpdateAffected==1) return 1;
			else return 0;
		}
		//글 좋아요 취소_delete(likeboard테이블),update(community테이블의 likecount-1)
		else {//이미 likeboard테이블에 있는데 또 눌렀다는거 == 좋아요 취소
			commuLikeDeleteAffected=dao.commuLikeDelete(map);
			commuLikeUpdateAffected=dao.commuLikeMinusUpdate(map);
			if(commuLikeDeleteAffected==1 && commuLikeUpdateAffected==1) return 1;
			else return 0;
		}
	}

	
	
}
