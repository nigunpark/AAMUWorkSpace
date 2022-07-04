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
	public List<String> commuSelectPhotoList(String cno){
		return template.selectList("commuSelectPhotoList");
	}
	

	//�� �����
	public int commuInsert(Map map) {
		return template.insert("commuInsert",map);
	}
	
	//photo �����
	public int photoInsert(Map map) {
		return template.insert("photoInsert",map);
	}
	
	//��� �����
	public int placeInsert(Map map) {
		return template.insert("placeInsert",map);
	}

}
