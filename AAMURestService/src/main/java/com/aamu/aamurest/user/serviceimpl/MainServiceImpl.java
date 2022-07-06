package com.aamu.aamurest.user.serviceimpl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.RouteDTO;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	private MainDAO dao;
	
///////////////////////////////////////////////////insert place impl
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
		
		return dao.dinerInsert(dto);
	}
	@Override
	public int eventInsert(AttractionDTO dto) {
		
		return dao.eventInsert(dto);
	}
	@Override
	public int plannerInsert(PlannerDTO dto) {
		
		return dao.plannerInsert(dto);
	}
	@Override
	public int RouteInsert(RouteDTO route) {
		// TODO Auto-generated method stub
		return dao.routeInsert(route);
	}
	
///////////////////////////////////////////////////get place impl
	@Override
	public List<RouteDTO> selectRouteList(int rbn) {
		
		return dao.selectRouteList(rbn);
	}
	@Override
	public List<AttractionDTO> selectPlacesList(Map map) {
		
		return dao.selectPlacesList(map);
	}
	@Override
	public List<AttractionDTO> selectAttrSigungu(Map map) {
		
		return dao.selectAttrSigungu(map);
	}
	@Override
	public List<AttractionDTO> selectEventSigungu(Map map) {
		
		return dao.selectEventSigungu(map);
	}
	@Override
	public List<AttractionDTO> selectHotelSigungu(Map map) {
		
		return dao.selectHotelSigungu(map);
	}
	@Override
	public List<AttractionDTO> selectDinerSigungu(Map map) {
		
		return dao.selectDinerSigungu(map);
	}
	@Override
	public List<AttractionDTO> selectEventList(Map map) {
		return dao.selectEventList(map);
	}
	@Override
	public List<AttractionDTO> selectHotelList(Map map) {
		return dao.selectHotelList(map);
	}
	@Override
	public List<AttractionDTO> selectDinerList(Map map) {
		return dao.selectDinerList(map);
	}
///////////////////////////////////////////////////search place impl
	@Override
	public List<AttractionDTO> searchPlace(Map map) {
		// TODO Auto-generated method stub
		return dao.searchPlace(map);
	}

	@Override
	public List<AttractionDTO> searchUrl(Map map) {
		
		return dao.searchUrl(map);
	}
///////////////////////////////////////////////////update place impl
	@Override
	public int updateUrl(AttractionDTO dto) {
		// TODO Auto-generated method stub
		return dao.updateUrl(dto);
	}
	////////////////////////////////////selectone place
	@Override
	public AttractionDTO selectOnePlace(int contentid) {
		
		return dao.selectOnePlace(contentid);
	}


}
