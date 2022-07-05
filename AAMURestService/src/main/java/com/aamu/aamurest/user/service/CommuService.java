package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//�� ��Ͽ�
	List<T> commuSelectList();
	
	//�� ��Ͽ�_��� �Ѱ� ��������
	CommuCommentDTO commuCommentSelectOne(String cno);
	
	//�� ��Ͽ�_���� ��������
	List commuSelectPhotoList(String cno);
	
	//Ŀ�´�Ƽ �ۻ���
	int commuInsert(Map map);

}
