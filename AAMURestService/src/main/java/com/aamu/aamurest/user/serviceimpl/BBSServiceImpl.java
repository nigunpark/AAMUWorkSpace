package com.aamu.aamurest.user.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;

@Service("bbsService")
public class BBSServiceImpl implements BBSService{
	
	@Autowired
	private BBSDao dao;
	
	//글 목록용
	@Override
	public List<BBSDTO> bbsSelectList() {
		return dao.bbsSelectList();
	}
	
	//글 목록용_사진 뿌려주기
	@Override
	public List bbsSelectPhotoList(int rbn) {
		return dao.bbsSelectPhotoList(rbn);
	}


}
