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
	
	//�� ��Ͽ�
	@Override
	public List<CommuDTO> commuSelectList() {
		return dao.commuSelectList();
	}
	
	//�� ��Ͽ�_����ϳ� �ѷ��ٰ�
	@Override
	public CommuCommentDTO commuCommentSelectOne(String cno) {
		return dao.commuCommentSelectOne(cno);
	}
	
	//�� ��Ͽ�_��������Ʈ
	@Override
	public List commuSelectPhotoList(String cno) {
		return dao.commuSelectPhotoList(cno);
	}
	
	//�� ����
	@Override
	public int commuInsert(Map map) {
		//commu
		int commuaffected=dao.commuInsert(map);
		//���� 
		int photoAffected=0;
		List<String> lists= (List<String>) map.get("photolist");
		for(String photo:lists) {
			Map photomap = new HashMap();
			photomap.put("lno", map.get("lno"));
			photomap.put("photo", photo);
			photoAffected+=dao.photoInsert(photomap);
		}
		//�÷��̽�
		if(map.get("place")!=null) { //�÷��̽��� �Ѿ�Դ�
			int placeAffected=dao.placeInsert(map); //insert �����ϸ� 1 
			if(placeAffected ==0) return 0; //�����ϸ� 0 ��ȯ
		}
		
		if(commuaffected==1 && photoAffected==((List)map.get("photolist")).size()) 
			return 1;
		else
			return 0;
	}/////commuInsert
	
}
