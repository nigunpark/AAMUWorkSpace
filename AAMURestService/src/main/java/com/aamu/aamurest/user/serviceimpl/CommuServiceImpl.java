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
	
	//글 좋아요_insert likeboard 테이블
	@Override
	public int commuLikeInsert(Map map) {
		return dao.commuLikeInsert(map);
	}
	
	
}
