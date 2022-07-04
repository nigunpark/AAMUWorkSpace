package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//Ŀ�´�Ƽ �۸��
	List<T> commuSelectList();
	
	//comment �Ѱ� ��������
	CommuCommentDTO commuCommentSelectOne(String cno);
	
	//����
	List commuSelectPhotoList(String cno);
	
	//Ŀ�´�Ƽ �ۻ���
	int commuInsert(Map map);

}
