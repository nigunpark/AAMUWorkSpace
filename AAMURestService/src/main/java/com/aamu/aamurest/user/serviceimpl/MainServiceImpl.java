package com.aamu.aamurest.user.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.PlannerDTO.Route;

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
	@Override
	public int plannerInsert(PlannerDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int RouteInsert(Route route) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<AttractionDTO> selectPlacesList(Map map) {
		
		return dao.selectPlacesList(map);
	}

}
