package com.aamu.aamurest.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CommuService<T> {
	//Ŀ�´�Ƽ ���
	List<T> commuSelectList();
	

}
