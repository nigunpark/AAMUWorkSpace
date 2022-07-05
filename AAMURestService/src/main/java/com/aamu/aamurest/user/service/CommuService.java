package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//�� ��Ͽ�
	List<T> commuSelectList();
	
	//�� ��Ͽ�_��� �Ѱ� ��������
	CommuCommentDTO commuCommentSelectOne(String lno);
	
	//�� ��Ͽ�_���� ��������
	List commuSelectPhotoList(String lno);
	
	//�� ������
	int commuInsert(Map map);
	
	//!!!!!!!!!!�� ������_��� �ѷ��ֱ�
	List commuPlaceList(Map map);
	
	//�� ������
	int commuUpdate(CommuDTO dto);

}
