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
		for(String photo:lists) {
			Map photomap = new HashMap();
			photomap.put("lno", map.get("lno"));
			photomap.put("photo", photo);
			photoAffected+=dao.photoInsert(photomap);
		}
		//장소
		if(map.get("place")!=null) {
			int placeAffected=dao.placeInsert(map); 
			if(placeAffected ==0) return 0; 
		}
		
		if(commuaffected==1 && photoAffected==((List)map.get("photolist")).size()) 
			return 1;
		else
			return 0;
	}/////commuInsert
	
	//!!!!!!!!!!글 생성용_장소 뿌려주기
	@Override
	public List<Map> commuPlaceList(Map map) {
		map.put("searchWord",map.get("searchWord"));
		return dao.commuPlaceList(map);
	}

	//글 수정용
	@Override
	public int commuUpdate(CommuDTO dto) {
		return dao.commuUpdate(dto);
		
	}
	
}
