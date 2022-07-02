package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.CommuService;

@Service("commuService")
public class CommuServiceImpl implements CommuService<CommuDTO>{
	@Autowired
	private CommuDAO dao;
	
	@Override
	public List<CommuDTO> commuSelectList() {
		return dao.commuSelectList();
	}
	
	@Override
	public int commuInsert(Map map) {
		//commu
		int affected=dao.commuInsert(map);
		//사진 
		int photoAffected=dao.photoInsert(map);
		
		if(map.get("place")!=null) { //플레이스가 넘어왔다
			int placeAffected=dao.placeInsert(map); //insert 성공하면 1 실패하면 0 반환
			if(placeAffected ==0) return 0;
		}
		
		if(affected==1 && photoAffected==((List)map.get("photo")).size())
			return 1;
		else
			return 0;
		
	}
	
}
