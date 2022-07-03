package com.aamu.aamurest.user.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	private MainDAO dao;
	@Override
	public int placeInsert(AttractionDTO dto) {
		
		return dao.placeInsert(dto);
	}
	@Override
	public int infoInsert(AttractionDTO dto) {
		
		return dao.infoInsert(dto);
	}
	@Override
	public int hotelInsert(AttractionDTO dto) {
		
		return dao.hotelInsert(dto);
	}
	@Override
	public int dinerInsert(AttractionDTO dto) {
		// TODO Auto-generated method stub
		return dao.dinerInsert(dto);
	}
	@Override
	public int eventInsert(AttractionDTO dto) {
		return dao.eventInsert(dto);
	}

}
