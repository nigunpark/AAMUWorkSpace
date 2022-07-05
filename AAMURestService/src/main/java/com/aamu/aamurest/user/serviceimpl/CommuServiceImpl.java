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
	
	//글 목록용_댓글하나 뿌려줄거
	@Override
	public CommuCommentDTO commuCommentSelectOne(String cno) {
		return dao.commuCommentSelectOne(cno);
	}
	
	//글 목록용_사진리스트
	@Override
	public List commuSelectPhotoList(String cno) {
		return dao.commuSelectPhotoList(cno);
	}
	
	//글 생성
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
		//플레이스
		if(map.get("place")!=null) { //플레이스가 넘어왔다
			int placeAffected=dao.placeInsert(map); //insert 성공하면 1 
			if(placeAffected ==0) return 0; //실패하면 0 반환
		}
		
		if(commuaffected==1 && photoAffected==((List)map.get("photolist")).size()) 
			return 1;
		else
			return 0;
	}/////commuInsert
	
}
