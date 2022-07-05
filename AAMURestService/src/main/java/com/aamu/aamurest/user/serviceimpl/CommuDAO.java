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
	public CommuCommentDTO commuCommentSelectOne(String lno) {
		return template.selectOne("commuCommentSelectOne",lno);
	}
	
	//�� ��Ͽ�_���� ����Ʈ ��������
	public List commuSelectPhotoList(String lno){
		return template.selectList("commuSelectPhotoList",lno);
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
	
	//!!!!!!!�� �����_��� �ѷ��ֱ�
	public List commuPlaceList(Map map) {
		System.out.println("map �Ѿ�Դ�:"+map);
		return template.selectList("commuPlaceList",map);
	}
	
	//�� ������
	public int commuUpdate(CommuDTO dto) {
		return template.update("commuUpdate",dto);
	}
	
	//�� ������-��� ����
	public int placeUpdate(CommuDTO dto) {
		return template.update("commuPlaceUpdate",dto);
	}

}
