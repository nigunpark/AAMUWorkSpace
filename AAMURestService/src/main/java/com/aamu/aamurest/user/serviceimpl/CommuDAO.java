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
	
	//�� ��Ͽ�
	public List<CommuDTO> commuSelectList(){
		return template.selectList("commuSelectList");
	}
	
	//�� ��Ͽ�_��� �ϳ� �������°�
	public CommuCommentDTO commuCommentSelectOne(String cno) {
		return template.selectOne("commuCommentSelectOne",cno);
	}
	
	//�� ��Ͽ�_���� ����Ʈ ��������
	public List commuSelectPhotoList(String cno){
		return template.selectList("commuSelectPhotoList",cno);
	}
	
	//�� �����
	public int commuInsert(Map map) {
		System.out.println("map:"+map);
		return template.insert("commuInsert",map);
	}
	
	//�� �����_photo ����
	public int photoInsert(Map map) {
		return template.insert("commuPhotoInsert",map);
	}
	
	//�� �����_��� ����
	public int placeInsert(Map map) {
		return template.insert("commuPlaceInsert",map);
	}

}
